update AD_UI_Element e set AD_Tab_ID=(
	select s.AD_Tab_ID
	from AD_UI_ElementGroup eg
	inner join AD_UI_Column c on (c.AD_UI_Column_ID=eg.AD_UI_Column_ID)
	inner join AD_UI_Section s on (s.AD_UI_Section_ID=c.AD_UI_Section_ID)
	where true
	and eg.AD_UI_ElementGroup_ID=e.AD_UI_ElementGroup_ID
)
where AD_Tab_ID is null;

