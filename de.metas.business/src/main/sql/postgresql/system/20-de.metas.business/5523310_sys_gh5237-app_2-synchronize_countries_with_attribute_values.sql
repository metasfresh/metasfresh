CREATE TABLE backup.m_attributevalue_2019_05_31 AS
SELECT * FROM m_attributevalue;

UPDATE m_attributevalue SET isactive=c_country.isactive,name=c_country.name,updated=now(),updatedby=99
FROM c_country
WHERE 
m_attribute_id::text=(SELECT value FROM ad_Sysconfig WHERE name='de.metas.swat.CountryAttribute')
AND c_country.countrycode=m_attributevalue.value
AND (c_country.name!=m_attributevalue.name
OR c_country.isactive!=m_attributevalue.isactive);

