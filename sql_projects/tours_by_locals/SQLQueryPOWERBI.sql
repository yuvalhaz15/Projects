create view Tours_Country as
 select t.Country
from tours as t


select * 
from Tours_Country


DROP view Country_and_guides
create view Country_and_guides as
select t.Country,[full name]=g.FirstName+' '+g.LastName,g.ID, Rating=dbo.getGuideAvgReview(g.ID)
from tours as t join guides as g on t.GuideID=g.ID
WHERE g.FirstName+' '+g.LastName IS NOT NULL

select *
from Country_and_guides
drop view TOTAL_INCOME


-----------------------------------------------הכנסה כוללת כל שנה וכל חודש בשנה-----------------------------
create view TOTAL_INCOME as
SELECT distinct Year=YEAR(d.startdate),month=month(d.startdate),[Total revenues]=sum(o.Price)
FROM Details AS D join orders as o on o.OrderNum=d.OrderNum
group by YEAR(d.startdate),month(d.startdate)
order by 1

select * 
from TOTAL_INCOME


----------------------------------------לקוחות חדשים בחברה בכל שנה-------------------------


drop view new_Customers_PerYear
create view new_Customers_PerYear as

select YEAR=YEAR (o1.OrderDT),[New Customers]=count(*)
from orders as o1
               join (select    c.Email,[ first order]=min(o.OrderDT)
                     from Customers as c join Orders as o on c.Email=o.Email
                     group by c.Email) as x on o1.OrderDT=x.[ first order]
                     
 group by YEAR (o1.OrderDT)



 -----------------מחיר ממוצע שהוציא לקוח כל שנה --------
 create view Avg_Price_Per_customer_Per_Year as 
 select x.Year,amount=sum(t.[Total revenues])/x.[NumberOf Customers]
 from(  select distinct y.Year,[NumberOf Customers]=count(y.Email)
      from
       (select  distinct Year=YEAR(o.OrderDT), (o.Email)
      from Orders as o join TOTAL_INCOME as t on t.Year=YEAR(o.OrderDT)
	         group by YEAR(o.OrderDT), (o.Email))  as y 
	   
                 group by y.Year ) as x join TOTAL_INCOME as t on x.Year=t.Year
group by x.Year,x.[NumberOf Customers]




--מספר ההזמנות בהם טיילו מסודרים לפי שנה- לשנות את נתוני ההזמנות כך שיתאים לדוח

Create View V_CountriesPerYear AS
Select  Year (O.OrderDT) as [Year], count (distinct T.Country) as [Number of countries]
From Tours as T Join Details as D on T.TourNum = D.TourNum Join Orders as O on D.OrderNum = O.OrderNum
Group By Year (O.OrderDT)

select *
from V_CountriesPerYear

Select C.Country , F.Filter, count (*) as [Number of times]
From Filters as F Join SearchVirtuals as SV on F.IP = SV.IP AND F.DT = SV.DT Join Customers as C on SV.Email = C.Email
Where YEAR (SV.DT) >2018 
Group By C.Country , F.Filter
Order By C.Country, count (*) DESC 