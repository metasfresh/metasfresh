
-- DROP TABLE IF EXISTS TEMP_Missing_WINDOW_Element_Names;
CREATE TABLE TEMP_Missing_MENU_Element_Names
AS
SELECT  ad_MENU_id, Name, entityType, COALESCE(Description,'') AS Description
FROM AD_MENU ;




--DROP TABLE IF EXISTS TEMP_Missing_MENU_Elements_Inserts;

CREATE TABLE TEMP_Missing_MENU_Elements_Inserts
AS
SELECT n.AD_MENU_ID, 

'INSERT INTO AD_ELEMENT (ad_element_id,ad_client_id ,ad_org_id ,isactive ,created ,createdby,updated ,updatedby ,entitytype  ,name , printname , description )' ||
'( SELECT  nextval(' || '''ad_element_seq''' || ') AS AD_Element_ID,  0 AS ad_client_id ,  0 AS ad_org_id ,' || '''Y''' || '  AS isactive ,  now() as created ,  100 AS createdby,  now() AS updated ,  100 AS updatedby , '||

 '''' || COALESCE(n.entityType, '') ||  '''' || ' AS entitytype  ,'||
 '''' ||COALESCE(n.name, '') ||  '''' || ' AS  name ,'||
 '''' || COALESCE(n.name, '') ||  '''' || ' AS  printname ,'||
 '''' ||COALESCE(n.description, '')  ||  '''' || ' AS description );' :: text as INSERT_ELEMENT,

 'UPDATE AD_MENU SET AD_Element_ID = ' || 'currval(''ad_element_seq'') WHERE AD_MENU_ID = ' || n.AD_MENU_ID||';' AS UPDATE_AD_MENU 

 FROM TEMP_Missing_MENU_Element_Names n;