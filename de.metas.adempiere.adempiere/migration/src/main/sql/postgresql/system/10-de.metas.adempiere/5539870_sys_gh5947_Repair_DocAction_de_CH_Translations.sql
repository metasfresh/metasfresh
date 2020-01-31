

UPDATE AD_Ref_List_Trl trl
SET Name = x.Name
from
(
	select t.Name ,  t.ad_language, t.AD_Ref_List_ID
	FROM AD_Ref_List_Trl t
	JOIN AD_Ref_List l on t.AD_Ref_List_ID = l.AD_Ref_List_ID
	where ad_language = 'de_DE' and l.AD_Reference_ID=135
) x
where  trl.AD_Ref_List_ID = x.AD_Ref_List_ID and trl.ad_language = 'de_CH';