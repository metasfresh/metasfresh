-- Reset IncludedTabHeight for C_OrderLine tabs
update AD_Field set IncludedTabHeight=0 where AD_Field_ID IN (1000000, 540012) and IncludedTabHeight=800;

/* Script to check other IncludedTabHeights
select w.Name, tt.Name, t.TableName, f.IncludedTabHeight, f.AD_Field_ID
from AD_Field  f
inner join AD_Tab tt on (tt.AD_Tab_ID=f.AD_Tab_ID)
inner join AD_Table t on (t.AD_Table_ID=tt.AD_Table_ID)
inner join AD_Window w on (w.AD_Window_ID=tt.AD_Window_ID)
where f.IncludedTabHeight > 0
order by t.TableName, f.IncludedTabHeight desc
;
*/
