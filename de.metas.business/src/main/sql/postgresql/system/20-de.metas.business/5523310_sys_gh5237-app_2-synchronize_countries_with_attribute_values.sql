update m_attributevalue set isactive=c_country.isactive,name=c_country.name,updated=now(),updatedby=99
from c_country
where 
m_attribute_id::text=(select value from ad_Sysconfig where name='de.metas.swat.CountryAttribute')
and c_country.countrycode=m_attributevalue.value
and (c_country.name!=m_attributevalue.name
or c_country.isactive!=m_attributevalue.isactive);

create backup.m_attributevalue_2019_05_31 as
select * from m_attributevalue;