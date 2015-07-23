use [master]
go
/****** object:  database [crops]    script date: 20/07/2015 02:29:28 ******/
create database [crops] on  primary 
( name = N'crops', filename = N'c:\program files (x86)\microsoft sql server\mssql11.sqlexpress\mssql\data\crops.mdf' , size = 3136kb , maxsize = unlimited, filegrowth = 1024kb )
 log on 
( name = N'crops_log', filename = N'c:\program files (x86)\microsoft sql server\mssql11.sqlexpress\mssql\data\crops_log.ldf' , size = 784kb , maxsize = 2048gb , filegrowth = 10%)
go
if (1 = fulltextserviceproperty('isfulltextinstalled'))
begin
exec [crops].[dbo].[sp_fulltext_database] @action = 'enable'
end
go
alter database [crops] set ansi_null_default off 
go
alter database [crops] set ansi_nulls off 
go
alter database [crops] set ansi_padding off 
go
alter database [crops] set ansi_warnings off 
go
alter database [crops] set arithabort off 
go
alter database [crops] set auto_close on 
go
alter database [crops] set auto_create_statistics on 
go
alter database [crops] set auto_shrink off 
go
alter database [crops] set auto_update_statistics on 
go
alter database [crops] set cursor_close_on_commit off 
go
alter database [crops] set cursor_default  global 
go
alter database [crops] set concat_null_yields_null off 
go
alter database [crops] set numeric_roundabort off 
go
alter database [crops] set quoted_identifier off 
go
alter database [crops] set recursive_triggers off 
go
alter database [crops] set  enable_broker 
go
alter database [crops] set auto_update_statistics_async off 
go
alter database [crops] set date_correlation_optimization off 
go
alter database [crops] set trustworthy off 
go
alter database [crops] set allow_snapshot_isolation off 
go
alter database [crops] set parameterization simple 
go
alter database [crops] set read_committed_snapshot off 
go
alter database [crops] set honor_broker_priority off 
go
alter database [crops] set recovery simple 
go
alter database [crops] set  multi_user 
go
alter database [crops] set page_verify checksum  
go
alter database [crops] set db_chaining off 
go
use [crops]
go
/****** object:  table [dbo].[crop]    script date: 20/07/2015 02:29:28 ******/
set ansi_nulls on
go
set quoted_identifier on
go
set ansi_padding on
go
create table [dbo].[crop](
	[id] [int] identity(1,1) not null,
	[parish] [varchar](120) null,
	[croptype] [varchar](max) null,
	[lowerprice] [money] null,
	[upperprice] [money] null,
	[freqprice] [money] null,
	[supplystatus] [varchar](120) null,
	[quality] [varchar](115) null,
	[pricemonth] [datetime] null,
	[xcoord] [float] null,
	[ycoord] [float] null,
 constraint [pk__crop__3213e83f09e8dc34] primary key clustered 
(
	[id] asc
)with (pad_index = off, statistics_norecompute = off, ignore_dup_key = off, allow_row_locks = on, allow_page_locks = on) on [primary]
) on [primary] textimage_on [primary]
go
set ansi_padding off
go
set identity_insert [dbo].[crop] on 
insert [dbo].[crop] ([id], [parish], [croptype], [lowerprice], [upperprice], [freqprice], [supplystatus], [quality], [pricemonth], [xcoord], [ycoord]) values (49, N'clarendon ', N'pumpkin ', 55.0000, 55.0000, 55.0000, N'scarce ', N'average', cast(N'2010-11-01 00:00:00.000' as datetime), -77.28144617, 17.98163128)
insert [dbo].[crop] ([id], [parish], [croptype], [lowerprice], [upperprice], [freqprice], [supplystatus], [quality], [pricemonth], [xcoord], [ycoord]) values (50, N'clarendon ', N'green banana ', 33.0000, 44.0000, 33.0000, N'moderate', N'average', cast(N'2010-01-01 00:00:00.000' as datetime), -77.28430401, 18.0428326)
insert [dbo].[crop] ([id], [parish], [croptype], [lowerprice], [upperprice], [freqprice], [supplystatus], [quality], [pricemonth], [xcoord], [ycoord]) values (51, N'clarendon ', N'carrot ', 99.0000, 99.9900, 99.9900, N'moderate', N'average', cast(N'2010-08-01 00:00:00.000' as datetime), -77.34373128, 17.92811938)
insert [dbo].[crop] ([id], [parish], [croptype], [lowerprice], [upperprice], [freqprice], [supplystatus], [quality], [pricemonth], [xcoord], [ycoord]) values (52, N'clarendon ', N'green banana ', 44.0000, 44.0000, 44.0000, N'average ', N'good ', cast(N'2010-09-01 00:00:00.000' as datetime), -77.28893067, 18.05525808)
insert [dbo].[crop] ([id], [parish], [croptype], [lowerprice], [upperprice], [freqprice], [supplystatus], [quality], [pricemonth], [xcoord], [ycoord]) values (53, N'clarendon ', N's. bonnet pepper ', 88.0000, 99.9900, 99.9900, N'scarce ', N'average', cast(N'2010-06-01 00:00:00.000' as datetime), -77.27971086, 17.95386351)
insert [dbo].[crop] ([id], [parish], [croptype], [lowerprice], [upperprice], [freqprice], [supplystatus], [quality], [pricemonth], [xcoord], [ycoord]) values (54, N'clarendon ', N'callaloo ', 33.0000, 44.0000, 33.0000, N'scarce ', N'average', cast(N'2010-01-01 00:00:00.000' as datetime), -77.25070424, 17.92879983)
set identity_insert [dbo].[crop] off
use [master]
go
alter database [crops] set  read_write 
go
