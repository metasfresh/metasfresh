create table backup.AD_UI_Element_BKP_gh1665_5463611 as 
(select uie.*
from AD_UI_Element uie
left join AD_Field f on f.AD_Field_ID = uie.AD_Field_ID
left join AD_UI_ElementGroup uieg on uieg.AD_UI_ElementGroup_ID = uie.AD_UI_ElementGroup_ID
left join AD_UI_Column uic on uic.AD_UI_Column_ID = uieg.AD_UI_Column_ID
left join AD_UI_Section uis on uis.AD_UI_Section_ID = uic.AD_UI_Section_ID
left join AD_Tab t on t.AD_Tab_ID = uis.AD_Tab_ID
where true
and uie.isDisplayed = 'N'
and f.isDisplayed = 'Y'
and t.tablevel > 0
);

update AD_UI_Element
set isDisplayed = 'Y' 
where AD_UI_Element_ID in
(
select uie.AD_UI_Element_ID
from AD_UI_Element uie
left join AD_Field f on f.AD_Field_ID = uie.AD_Field_ID
left join AD_UI_ElementGroup uieg on uieg.AD_UI_ElementGroup_ID = uie.AD_UI_ElementGroup_ID
left join AD_UI_Column uic on uic.AD_UI_Column_ID = uieg.AD_UI_Column_ID
left join AD_UI_Section uis on uis.AD_UI_Section_ID = uic.AD_UI_Section_ID
left join AD_Tab t on t.AD_Tab_ID = uis.AD_Tab_ID
where true
and uie.isDisplayed = 'N'
and f.isDisplayed = 'Y'
and t.tablevel > 0
);


create table backup.AD_UI_ElementGroup_BKP_gh1665_5463611 as
(
select uieg.* 
from AD_UI_ElementGroup uieg
left join AD_UI_Column uic on uic.AD_UI_Column_ID = uieg.AD_UI_Column_ID
left join AD_UI_Section uis on uis.AD_UI_Section_ID = uic.AD_UI_Section_ID
left join AD_Tab t on t.AD_Tab_ID = uis.AD_Tab_ID
where true
and t.tablevel > 0
and uieg.uistyle != ''
);

update AD_UI_ElementGroup
set uistyle = '' 
where AD_UI_ElementGroup_ID in
(
select uieg.AD_UI_ElementGroup_ID
from AD_UI_ElementGroup uieg
left join AD_UI_Column uic on uic.AD_UI_Column_ID = uieg.AD_UI_Column_ID
left join AD_UI_Section uis on uis.AD_UI_Section_ID = uic.AD_UI_Section_ID
left join AD_Tab t on t.AD_Tab_ID = uis.AD_Tab_ID
where true
and t.tablevel > 0
and uieg.uistyle != ''
);