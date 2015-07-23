DROP DATABASE IF EXISTS TimeQ;
create database TimeQ;
use TimeQ;

Create table Contact (
    CompanyCode varchar(5),
    CustomerCode varchar(8),
    Category varchar(2),
    SequenceNumber varchar(2),
    ContactInfo varchar(30),
    status varchar(1),
	primary key(CompanyCode,Category,SequenceNumber)
);

create table CompanyType (
    CompanyType varchar(4) primary key,
    Description varchar(100),
    PolicyCode varchar(3),
	Category varchar(2)
);

create table Company (
    CompanyCode varchar(5) primary key,
    CompanyName varchar(40),
    companyType varchar(4),
    msgCellPhone varchar(11),
    companyPhone varchar(11),
    TimingQPhone varchar(11),
    emailAdress varchar(32), 
	countryCode varchar(5),
    address1 varchar(30),
    address2 varchar(30),
    District varchar(20),
    Parish varchar(20),
    PostalCode varchar(10),
    headCompany varchar(5),
    ContactName varchar(30),
    ContactPhone varchar(11),
    ContactEmail varchar(30),
    TqProcessCode varchar(2),
    messageIndicator varchar(3),
    policyCode varchar(3),
    status varchar(1)
);


create table Customer (  
    CompanyCode varchar(5),
	CustomerCode varchar(8),
    LastName varchar(20),
    FirstName varchar(20),
    MiddleName varchar(20),
    Title varchar(2),
    msgCellPhone varchar(11),
    ContactPhone varchar(11),
    emailAdress varchar(32), 
    DateOfBirth datetime,
    Gender varchar(1),
    CustomerCategory varchar(2),
    AppointmentDate date,
    AppomentTime time,
    TimeServiced DateTime,
    QueueNumber varchar(5),
    policyCode varchar(3),
    MessageDeviceType varchar(3),
    MessagingMethod varchar(3),
    MessageAccountId varchar(16),
    TqProcessCode varchar(2),
    LastCompanyQueued varchar(5),
    advertisingIndicator boolean,
    Status varchar(1),
	primary key (CompanyCode , CustomerCode)
);

create table ServiceType (
    CompanyCode varchar(5),
    ServiceType varchar(3),
    Description varchar(20),
    Category varchar(2),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (CompanyCode , ServiceType)
);
 

Create table CustomerAppointment (
    CompanyCode varchar(5),
    Customercode varchar(8),
    AppointmentSequenceNum varchar(2),
    Appointmentdate date,
    Appointmenttime time,
    ServiceType varchar(3),
    StationCode varchar(2),
    PersonnelCode varchar(2),
    DepartureAddressFlag varchar(1),
    status varchar(1),
    ChangeReason varchar(2),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (CompanyCode , Customercode , AppointmentSequenceNum)
);

create table StationAppointment (
    CompanyCode varchar(5),
    stationCode varchar(2),
    Appointmentdate date,
    Appointmenttime time,
    CustomerCode varchar(8),
    AppointmentSequenceNum varchar(2),
    status varchar(1),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (CompanyCode , stationCode , Appointmentdate , Appointmenttime , CustomerCode)
);


create table MessagingMethod (
    MessagingMethodCode varchar(3) primary key,
    Description varchar(20),
    category varchar(2)
);

create table MessageDevice (
    MessageDeviceCode varchar(3) primary key,
    Description varchar(20),
    category varchar(2)
);

create table MessagingPriority (
    CustomerCode varchar(8),
    CompanyCode varchar(5),
    PrioritySequenceNumber varchar(3),
	msgDevType varchar(3),
	msgMethod varchar(3),
	msgAccount varchar(16),
    defaultmessage varchar(128),
    status varchar(1),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (CompanyCode , CustomerCode , PrioritySequenceNumber)
);

create table CustomerAddress (
    CompanyCode varchar(5),
    CustomerCode varchar(8),
    ApptSequenceNum varchar(2),
    countryCode varchar(5),
    addressLine1 varchar(30),
    addressLine2 varchar(30),
    District varchar(20),
    Parish varchar(20),
    PostalCode varchar(10),
    LikelyTransport varchar(3),
    DepartureTime DateTime,

    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (CompanyCode , CustomerCode , ApptSequenceNum)
);

Create table station (
    CompanyCode varchar(5),
    StationCode varchar(2),
    Description varchar(20),
    Category varchar(2),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (StationCode , CompanyCode)
);

create table ServiceBreakout (
    CompanyCode varchar(5),
    ServiceType varchar(3),
    BreakoutService varchar(3),
    status varchar(1),
    foreign key (CompanyCode , ServiceType)
        references ServiceType (CompanyCode , ServiceType),
    primary key (CompanyCode , BreakoutService , ServiceType)
);

create table policyElement (
    CompanyCode varchar(5),
    PolicyCode varchar(3),
    SequenceNumber varchar(2),
    PolicyElement varchar(3),
    status varchar(1),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (companyCode , policyCode , SequenceNumber)
);

Create table Companypolicy (
    CompanyCode varchar(5),
    PolicyCode varchar(3),
    Name varchar(20),
    Details text,
    Category varchar(2),
    status VARCHAR(1),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (CompanyCode , PolicyCode)
);


Create table ActivityType (
    CompanyCode varchar(5),
    ActivityTypeCode varchar(3),
    Descriptions VARCHAR(20),
	Category varchar(2),
    status VARCHAR(1),
    foreign key (CompanyCode)
        references company (CompanyCode),
	primary key(CompanyCode,ActivityTypeCode)
);


Create table ActivityTracking (
    CompanyCode varchar(5),
    CustomerCode varchar(8),
    SequencNumber varchar(2) , 
    ActivityType varchar(3),
    ActivityDate Date,
    ActivityTime time,
    detail VARCHAR(20),
    status VARCHAR(1),
    foreign key (CompanyCode)
        references company (CompanyCode),

    primary key (SequencNumber , CompanyCode , CustomerCode)
);

create table StatusOrCategory (
    BaseTable varchar(7),
    CategoryOrStatus varchar(2),
    Descritption varchar(20),
	primary key(BaseTable,CategoryOrStatus)
);

create table TQProcess (
    TQProcess varchar(2) primary key,
    Description varchar(20),
    Category varchar(2),
    Status varchar(1)
);


create table ChangeReason (
    CompanyCode varchar(5),
    ChangeReason varchar(2),
    Descritption varchar(20),
    category varchar(2),
    primary key (CompanyCode , ChangeReason)
);


create table OperatingHours (
    CompanyCode varchar(5),
    DayOfweek varchar(1),
    sequenceNumber varchar(2),
    AsOfDate dateTime,
    StartTime datetime,
    Endtime dateTime,
    status varchar(1),
    changeReason varchar(2),
    foreign key (CompanyCode)
        references company (CompanyCode),
    foreign key (CompanyCode , ChangeReason)
        references ChangeReason (CompanyCode , ChangeReason),
    primary key (CompanyCode , dayofweek , sequenceNumber)
);

create table personnelSchedule (
    CompanyCode varchar(5),
    personnelCode varchar(5),
    DayOfweek varchar(1),
    sequenceNumber varchar(2),
    AsOfDate date,
    StartTime datetime,
    Endtime dateTime, 
    StationCode varchar(2),
    staus varchar(1),
    ChangeReason varchar(2),
    foreign key (CompanyCode , ChangeReason)
        references ChangeReason (CompanyCode , ChangeReason),
    foreign key (CompanyCode , ChangeReason)
        references ChangeReason (CompanyCode , ChangeReason),
    primary key (personnelCode , CompanyCode , dayofweek , sequenceNumber)
);

create table CompanyPersonnel (
    CompanyCode varchar(5),
    personnelCode varchar(5),
    name varchar(20),
    policyCode varchar(3),
    StationCode varchar(2),
    status varchar(1),
    foreign key (CompanyCode)
        references company (CompanyCode),
    primary key (personnelCode , CompanyCode)
);




