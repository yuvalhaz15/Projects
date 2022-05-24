	
create table GUIDES(
	ID				int not null,
	FirstName		varchar(50) null,
	LastName		varchar(50) null,
	FirstTourOn		date default getDate(),
	constraint Guides_Pk Primary key(ID)
)


create table TOURS (
	TourNum			int not null,
	TourName		varchar(200) not null,
	Country			varchar(50) not null,
	Area			varchar(50) not null,
	Currency		varchar(5) default 'USD',
	Price			SmallMoney null default 0,
	ActivityLevel	varchar(50) not null,
	GuideID			int not null,

	constraint Price_Pos check(price>=0 ),
	constraint Tours_Pk Primary key(TourNum),
	constraint Tours_Fk FOREIGN KEY (GuideID) REFERENCES GUIDES (ID)
)


create table TOURTYPES(
	TourNum		int not null,
	TourType	varchar(50) Not null,

	constraint TOURTYPES_Pk Primary key(TourNum, TourType),
	constraint TOURTYPES_Fk FOREIGN KEY (TourNum) 	REFERENCES 	TOURS (TourNum)
)


create table LANGUAGES(
	GuideId	int not null,
	LanguageName	varchar(50) not null,

	constraint LANGUAGES_Pk Primary key(GuideId,LanguageName),
	constraint LANGUAGES_Fk FOREIGN KEY (GuideId) REFERENCES 	Guides (id)
)


create table VIRTUALTOURS(
	TourNum			int not null,
	NextTourVoucher	smallmoney,
	VoucherCurrency varchar(5),

	constraint VIRTUALTOURS_Pk Primary key(TourNum),
	constraint  VIRTUALTOURS_Fk FOREIGN KEY (TourNum) 	REFERENCES 	TOURS ( TourNum)
)


CREATE TABLE Customers (
	Email			Varchar(50) NOT NULL,
	UserPassword	Varchar(50) NOT NULL,
	Name			Varchar(50) NOT NULL,
	Country			varchar(50) NOT NULL,
	City			Varchar(50) NOT NULL,
	Street			varchar(50) NOT NULL,
	StreetNum		int NOT NULL,
	ZipCode			varchar(20),

	CONSTRAINT pk_CUSTOMERS PRIMARY KEY (Email),
	CONSTRAINT  ck_EMAIL  CHECK (Email LIKE '%@%.%'),
)



CREATE TABLE CreditCards (
	CCN				varchar(16) NOT NULL,
	CreditCardType  Varchar(50) NOT NULL,
	CVV				varchar (3) NOT NULL,
	ExpiryDate		Date NOT NULL,

	CONSTRAINT pk_CreditCards PRIMARY KEY (CCN),
	CONSTRAINT  ck_ExpiryDate   CHECK (ExpiryDate > getDate())
)


CREATE TABLE Orders (
	OrderNum		int NOT NULL,
	OrderDT			DateTime,
	Price			SmallMoney DEFAULT 0,
	Currency		varchar(5) default 'usd',
	Email			varchar(50) NOT NULL,
	CCN				varchar(16) NOT NULL,

	CONSTRAINT pk_Orders PRIMARY KEY (OrderNum),
	CONSTRAINT  fk_Customers FOREIGN KEY (Email) REFERENCES Customers (Email),
	CONSTRAINT  fk_CreditCards FOREIGN KEY (CCN) REFERENCES CreditCards (CCN),
	CONSTRAINT  ck_NonNegativityPrice CHECK (Price>=0)
)
drop table WISHES

create table WISHES(
	Email		varchar(50) not null,
	TourNum		int not null,
	DT          dateTime ,
    Already_Traveled varchar(5) default 'No',
	CONSTRAINT pk_WISHES primary key(Email,TourNum),
	CONSTRAINT  fk_WISHES1 FOREIGN KEY (Email) REFERENCES Customers (Email),
	CONSTRAINT  fk_WISHES2 FOREIGN KEY ( TourNum) 	REFERENCES 	TOURS ( TourNum)
)



create table Details(
	OrderNum	int  not null,
	TourNum		int not null,
	TouristsNum		Tinyint  DEFAULT 1,
	startdate date,
	endDate    date 
	CONSTRAINT pk_ORDERDTOURS primary key(OrderNum,TourNum),
	CONSTRAINT  fk_ORDERDTOURS1 FOREIGN KEY (OrderNum) REFERENCES ORDERS (OrderNum),
	CONSTRAINT  fk_ORDERDTOURS2 FOREIGN KEY ( TourNum) 	REFERENCES  TOURS ( TourNum)
)

create table SEARCHVIRTUALS(
	IP			Varchar(50) not null,
	DT			DateTime not null,
	FromDate	Date ,
	ToDate		Date,
	Country		varchar(50),
	Email		varchar(50),

	constraint Ip_Good  CHECK (IP LIKE '%.%.%.%'),
	CONSTRAINT pk_SEARCHVIRTUALS PRIMARY KEY (IP,DT),
	CONSTRAINT  fk_SEARCHVIRTUALS FOREIGN KEY (Email) REFERENCES Customers (Email)
)


create table SEARCHTOURS(
	IP			Varchar(50)	not null,
	DT			DateTime not null,
	Area		varchar(50),
	TourName	varchar(50),
	TourType	varchar(50),	

	CONSTRAINT pk_SEARCHTOURS PRIMARY KEY (IP,DT),
	CONSTRAINT  fk_SEARCHTOURS FOREIGN KEY (IP,DT) REFERENCES SEARCHVIRTUALS (IP,DT)
)


create table ToursSearches(
	IP				varchar(50) not null,
	DT				DateTime not null,
	TourNum			int not null,

	constraint TOURSFILTERS_PK Primary key(TourNum,IP,DT),
	constraint  TOURSFILTERS_Fk1 FOREIGN KEY ( TourNum) REFERENCES 	TOURS ( TourNum),
	constraint  TOURSFILTERS_Fk2 FOREIGN KEY ( IP,DT) REFERENCES SEARCHVIRTUALS ( IP,DT)
)


 create table GuidesSearches(
	IP				varchar(50) not null,
	DT				DateTime not null,
	GuideID			int not null,

	CONSTRAINT pk_GUIDESFILTERS PRIMARY KEY (IP,DT,GuideID),
	CONSTRAINT  fk_GUIDESFILTERS1 FOREIGN KEY (IP,DT) REFERENCES SEARCHVIRTUALS (IP,DT),
	CONSTRAINT  fk_GUIDESFILTERS2 FOREIGN KEY ( GuideID) REFERENCES GUIDES ( ID)
 )
 create Table Filters(
 	IP		varchar(50) not null,
 	DT		 DateTime not null,
 	Filter varChar (100) Default 'ANY',
 

 CONSTRAINT pk_filters PRIMARY KEY (IP,DT,Filter),
 CONSTRAINT  fk_filters1 FOREIGN KEY (IP,DT) REFERENCES SEARCHVIRTUALS (IP,DT)
 

 )



 create table REVIEWS(
	Email	varchar(50) not null, 
	ReviewDT	DateTime ,
	Rating real	,
	FreeTxt	varchar(2000),
	OfTour int,
	

	constraint Rating_lim check(Rating>=0 and Rating<=5),
	CONSTRAINT  Pk_REVIEWS PRIMARY KEY (Email,ReviewDT),
	CONSTRAINT  fk_REVIEWS1 FOREIGN KEY (Email) REFERENCES Customers(Email) ,
	CONSTRAINT  fk_REVIEWS2 FOREIGN KEY ( ofTour) REFERENCES 	TOURS ( TourNum),
	
 )
 create table GUIDESRESPONES(
 	Email	varchar(50) not null, 
 	ReviewDT dateTime  not null,
 	GuideId int not null,
 	ResponeDT dateTime not null,
 	FreeTxt VarChar(2000) 

 constraint Pk_GuidesRespones primary key( ReviewDT ,GuideId ,ResponeDT),
	CONSTRAINT  fk_GUIDESRESPONES1 FOREIGN KEY (Email,ReviewDT) REFERENCES REVIEWS (EMAIL,ReviewDT) ,
	CONSTRAINT  fk_GUIDESRESPONES2 FOREIGN KEY ( GuideId) REFERENCES 	guides ( id)
	
 )
 
 create table Contacts(
 	GuideID int not null,
 	Email varchar(50) not null,
 	DT dateTime not null,
 	CustomerMassage varchar(2000),
 	DateOfTour date ,
 	NumOfPeople tinyint

  constraint Pk_Contacts primary key( Email , GuideID ,DT)
	CONSTRAINT  fk_Contacts1 FOREIGN KEY (Email) REFERENCES CUSTOMERS (Email) ,
	CONSTRAINT  fk_Contacts2 FOREIGN KEY ( GuideId) REFERENCES 	guides ( id)
	
 )
 CREATE TABLE COUNTRIES(
	Country		varChar(50) Not null,

	constraint pk_country primary Key (Country),
)


ALTER TABLE TOURS
	ADD CONSTRAINT fk_countries FOREIGN key(Country) references COUNTRIES (Country)


CREATE TABLE LANGUAGESLIST(
	LANGUAGENAME	varChar(50) Not null,

constraint pk_LANGUAGES primary Key (LANGUAGENAME),
)


ALTER TABLE Languages
	ADD CONSTRAINT fk_Languages FOREIGN key(LanguageName) references LANGUAGESLIST (LANGUAGENAME)	
	
	------------------CREATE TABLES------------------
	-------------------Querrys--------------
SELECT Y.TourName,Y.TourName,Y.[total revenue],X.Country,X.[PRICE FROM COUNTRY]
FROM	(select c.Country,[PRICE FROM COUNTRY]=sum(O.Price)
	from customers as c join orders as o on c.Email=o.Email 
	GROUP BY C.Country) AS X JOIN
	
	(Select t.TourName,t.Country,t.TourNum, [total revenue]=(sum(t.Price))
	from Details as d join tours as t on d.TourNum=t.TourNum join Orders as o on o.OrderNum=d.OrderNum
	
	group by t.TourName,t.TourNum,t.Country
	)AS Y ON X.Country=Y.Country 
	
	ORDER BY X.[PRICE FROM COUNTRY] DESC 
------------------------------------------    שאילתא המחזירה טבלה של המדינו בהן סכום ההזמנות בשנתיים האחרונות היה  גדול מ1500 מסדורת על פי סכום ההזמנות בסדר יורד -------
	select t.Country,[Total orders price]=SUM(o.Price)

	from tours as t join details as d on t.TourNum=d.TourNum join orders as o on o.OrderNum=d.OrderNum
	where year(GETDATE())-year(d.startdate)<2
	group by t.Country
	having SUM(o.Price)>1500
	order by 2 desc



	------------------הטיולים הכי רווחים מסודרים על פי סדר רווחיותם ,סכום ההכנסות שלהם ומספר ההזמנות , אשר חלו בחמש שנים האחרונות ----------------
	Select  t.TourName,t.Country,t.TourNum, [total revenue]=(sum(o.Price)),[number Of Orderds]=(count(d.TourNum)),d.startdate
	from Details as d join tours as t on d.TourNum=t.TourNum join Orders as o on o.OrderNum=d.OrderNum
	where year(GETDATE())-year(d.startdate)<5
	group by t.TourName,t.TourNum,t.Country,d.startdate
	
	ORDER BY 4 DESC
	--------------------------------------------------------------------------------------------------------------------------------
	----------------------פונקציה שמקבל מזהה של מדריך ומחזירה את ממוצע הביקורות שקיבל-------------
	CREATE FUNCTION getGuideAvgReview ( @ID int )  
RETURNS  Real
AS 	BEGIN

		DECLARE 	@Score	Real
				
				SELECT 	@Score = avg (r.Rating) 
				FROM	reviews as r join tours as t on r.OfTour=t.TourNum join guides as g on g.ID=t.GuideID
				WHERE 	g.ID = @ID

		RETURN 		@Score
		END

		
select  g.FirstName, g.ID,Avgreview=dbo.getGuideAvgReview(g.ID)
from GUIDES as g
order by 3 desc
	------------------------FUNCTION------------------------------------------------------------------------------------------------------
-------------------------------   לכל שפה שנבחרת להציג את רשימת המדריכים (שם ומזהה) שמדברים בשפה הזאת------------------------------------
	drop function Guides_languge
	CREATE 		FUNCTION    Guides_languge ( @Language   varchar (40)) 
RETURNS 	TABLE
AS		RETURN
SELECT	[Full name]=g.FirstName+' '+g.LastName,g.ID
FROM	GUIDES as g join  LANGUAGES as l on l.GuideId=g.ID
WHERE  l.LanguageName=@Language 	
	

	
	 select [Full name],[ID]
	from  Guides_languge('English')
	----------------------------------------------------------------
	---------------- ומראה טבלה של שם הלקוח אשר מקושר לאימייל ואת כל הטיולים אשר הזמין, מחיר ותאריך  EMAIL פרוצדורה שמורה אשר מקבלת ---------------

	CREATE PROCEDURE SP_TripBY_CustomerInfo 	@Email varchar(50)   
	as
	SELECT c.Name ,t.TourNum,o.Price,d.startdate,d.endDate
	FROM 	CUSTOMERS as c join Orders as o on c.Email=o.Email join Details as d on d.OrderNum=o.OrderNum  join tours as t on t.TourNum=d.TourNum
	WHERE 	c.Email = @Email

	EXECUTE SP_TripBY_CustomerInfo   'aglnlirf.aqacuueehu@ixgtfnvn.lzdune.com'

	--------------------------------------------------------------------------
	CREATE VIEW PaymentsDetails_view as 
SELECT   O.OrderDT , o.OrderNum , O.Email, CU.Name , CC.CCN , CC.CreditCardType , CC.CVV , CC.ExpiryDate ,
			 o.Price 
FROM CreditCards as CC join Orders as O on CC.CCN=O.CCN 
			join Customers as CU on O.Email=CU.Email
















	drop table Contacts
	drop table GUIDESRESPONES
	drop table REVIEWS
	drop table Filters
	drop table GuidesSearches
    drop table ToursSearches
	drop table SEARCHTOURS
	drop table SEARCHVIRTUALS
	drop table Details
	drop table WISHES
	drop table Orders
	drop table CreditCards
	drop table VIRTUALTOURS
	drop table LANGUAGES
	drop table TOURTYPES
	drop table TOURS
	drop table GUIDES
	drop table Customers
	drop table LANGUAGESLIST
	drop table COUNTRIES
	

