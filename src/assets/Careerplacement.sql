/*create database CareerPlacement*/
use CareerPlacement;

create table Company 
(
	CompanyId int not null identity,
	CompanyName varchar(250),
	CompanyAddress varchar(250),
	CompanyRepLastName varchar(255),
	CompanyRepfirstName varchar(255),
	CompanyURL varchar(255),
	CompanyDescription text,
	DateDeleted DATETIME,
    IsDeleted bit,
    LastModifiedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DateAdded DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key(CompanyId)
);

create  table Job
(
	JobId int not null identity,
	JobName varchar(255),
	JobDescription text,
	DateDeleted DATETIME,
    IsDeleted bit,
    LastModifiedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DateAdded DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key(jobId)
);

create table [Message]
(
 MessageId int not null identity,
 [Subject] varchar(256),
 Content text,
 DateDeleted DATETIME,
 IsDeleted bit,
 LastModifiedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 DateAdded DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 UserId int foreign key  references [user](userid),
 primary key(MessageId)
);



create table Requirement
(
  RequirementId int not null identity,
  JobRequirement varchar(255),
  DateAdded DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  IsDeleted bit,
  LastModifiedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  DateDeleted DATETIME,
  primary key(requirementId)
);


create table Comment
(
	CommentId int not null identity,
	CommentDetail text,
	Userid  int not null,
	DateDeleted DATETIME,
    IsDeleted bit,
    LastModifiedDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DateAdded DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	foreign key (userid) references [user](userid),
	primary key(Commentid)
);

