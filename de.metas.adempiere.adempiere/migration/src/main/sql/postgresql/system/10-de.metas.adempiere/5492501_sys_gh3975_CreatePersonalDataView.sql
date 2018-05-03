CREATE OR REPLACE VIEW RV_PersonalDataTableAndColumn AS
select t.TableName, c.ColumnName, coalesce(c.PersonalDataCategory, t.PersonalDataCategory) as PersonalDataCategory
from AD_Table t
join AD_Column c on (t.AD_Table_ID = c.AD_TAble_ID)
where t.PersonalDataCategory!='NP' or c.PersonalDataCategory!='NP';