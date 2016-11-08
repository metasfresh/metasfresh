/* Browse:

select aset.M_AttributeSet_ID, aset.Name as AttributeSet, a.Value as AttributeCOde, a.Name as AttributeName, a.M_Attribute_ID
from M_Attribute a
left outer join M_AttributeUse au on (au.M_Attribute_ID=a.M_Attribute_ID)
left outer join M_AttributeSet aset on (aset.M_AttributeSet_ID=au.M_AttributeSet_ID)
where a.Value='IsQualityInspection';
*/

--
-- Backup
create table backup.M_AttributeUse_bkp20161028 as select * from M_AttributeUse;

--
-- Delete "IsQualityInspection" attribute usage
delete from M_AttributeUse where M_Attribute_ID=540021;

