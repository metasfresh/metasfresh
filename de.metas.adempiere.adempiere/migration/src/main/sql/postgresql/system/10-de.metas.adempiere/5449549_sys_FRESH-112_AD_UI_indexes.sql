drop index if exists AD_UI_Section_Parent;
create index AD_UI_Section_Parent on AD_UI_Section (AD_Tab_ID);

drop index if exists AD_UI_Column_Parent;
create index AD_UI_Column_Parent on AD_UI_Column(AD_UI_Section_ID);

drop index if exists AD_UI_ElementGroup_Parent;
create index AD_UI_ElementGroup_Parent on AD_UI_ElementGroup(AD_UI_Column_ID);

drop index if exists AD_UI_Element_Parent;
create index AD_UI_Element_Parent on AD_UI_Element(AD_UI_ElementGroup_ID);

drop index if exists AD_UI_ElementField_Parent;
create index AD_UI_ElementField_Parent on AD_UI_ElementField(AD_UI_Element_ID);

