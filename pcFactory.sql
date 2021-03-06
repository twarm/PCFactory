USE [master]
GO
/****** Object:  Database [PCFactory]    Script Date: 11/30/2014 11:49:26 PM ******/
CREATE DATABASE [PCFactory]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PCFactory', FILENAME = N'F:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\PCFactory.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'PCFactory_log', FILENAME = N'F:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\PCFactory_log.ldf' , SIZE = 2048KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [PCFactory] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PCFactory].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [PCFactory] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PCFactory] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PCFactory] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PCFactory] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PCFactory] SET ARITHABORT OFF 
GO
ALTER DATABASE [PCFactory] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PCFactory] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [PCFactory] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PCFactory] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PCFactory] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PCFactory] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PCFactory] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PCFactory] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PCFactory] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PCFactory] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PCFactory] SET  DISABLE_BROKER 
GO
ALTER DATABASE [PCFactory] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PCFactory] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PCFactory] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PCFactory] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PCFactory] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PCFactory] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PCFactory] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PCFactory] SET RECOVERY FULL 
GO
ALTER DATABASE [PCFactory] SET  MULTI_USER 
GO
ALTER DATABASE [PCFactory] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PCFactory] SET DB_CHAINING OFF 
GO
ALTER DATABASE [PCFactory] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PCFactory] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [PCFactory]
GO
/****** Object:  Table [dbo].[Authorizations]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Authorizations](
	[aid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[isdel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Comp_Location]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Comp_Location](
	[lid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[isdel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[lid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Components]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Components](
	[cid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](256) NOT NULL,
	[stock] [int] NOT NULL,
	[price] [real] NOT NULL,
	[isdel] [int] NULL,
	[lid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Dealers]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Dealers](
	[dealerid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[phone] [varchar](50) NULL,
	[isdel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[dealerid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Departments]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Departments](
	[did] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[budget] [real] NULL,
	[isdel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[did] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Departments_Managers]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Departments_Managers](
	[time_from] [date] NOT NULL,
	[time_to] [date] NOT NULL,
	[ssn] [varchar](9) NOT NULL,
	[did] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[time_from] ASC,
	[time_to] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Employees]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Employees](
	[ssn] [varchar](9) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[aid] [int] NOT NULL,
	[did] [int] NOT NULL,
	[since] [date] NOT NULL,
	[pw] [varchar](50) NOT NULL,
	[isdel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ssn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Products]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Products](
	[pid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](256) NOT NULL,
	[stock] [int] NOT NULL,
	[isdel] [int] NULL,
	[ssn] [varchar](9) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[pid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Products_Make_of]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products_Make_of](
	[pid] [int] NOT NULL,
	[cid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[pid] ASC,
	[cid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[SalesRecords]    Script Date: 11/30/2014 11:49:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SalesRecords](
	[sid] [int] IDENTITY(1,1) NOT NULL,
	[datetime] [date] NOT NULL,
	[num] [int] NOT NULL,
	[price] [real] NOT NULL,
	[dealerid] [int] NOT NULL,
	[pid] [int] NOT NULL,
	[isdel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[sid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Authorizations] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[Comp_Location] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[Components] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[Dealers] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[Departments] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[Employees] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[Products] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[SalesRecords] ADD  DEFAULT ((0)) FOR [isdel]
GO
ALTER TABLE [dbo].[Components]  WITH CHECK ADD FOREIGN KEY([lid])
REFERENCES [dbo].[Comp_Location] ([lid])
GO
ALTER TABLE [dbo].[Departments_Managers]  WITH CHECK ADD FOREIGN KEY([did])
REFERENCES [dbo].[Departments] ([did])
GO
ALTER TABLE [dbo].[Departments_Managers]  WITH CHECK ADD FOREIGN KEY([ssn])
REFERENCES [dbo].[Employees] ([ssn])
GO
ALTER TABLE [dbo].[Employees]  WITH CHECK ADD FOREIGN KEY([aid])
REFERENCES [dbo].[Authorizations] ([aid])
GO
ALTER TABLE [dbo].[Employees]  WITH CHECK ADD FOREIGN KEY([did])
REFERENCES [dbo].[Departments] ([did])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([ssn])
REFERENCES [dbo].[Employees] ([ssn])
GO
ALTER TABLE [dbo].[Products_Make_of]  WITH CHECK ADD FOREIGN KEY([cid])
REFERENCES [dbo].[Components] ([cid])
GO
ALTER TABLE [dbo].[Products_Make_of]  WITH CHECK ADD FOREIGN KEY([pid])
REFERENCES [dbo].[Products] ([pid])
GO
ALTER TABLE [dbo].[SalesRecords]  WITH CHECK ADD FOREIGN KEY([dealerid])
REFERENCES [dbo].[Dealers] ([dealerid])
GO
ALTER TABLE [dbo].[SalesRecords]  WITH CHECK ADD FOREIGN KEY([pid])
REFERENCES [dbo].[Products] ([pid])
GO
USE [master]
GO
ALTER DATABASE [PCFactory] SET  READ_WRITE 
GO
