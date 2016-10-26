

UPDATE M_AttributeValue av set IsActive = 'N' where exists (select 1 from M_Attribute a where av.M_Attribute_ID = a.M_Attribute_ID and a. isActive = 'N')
;