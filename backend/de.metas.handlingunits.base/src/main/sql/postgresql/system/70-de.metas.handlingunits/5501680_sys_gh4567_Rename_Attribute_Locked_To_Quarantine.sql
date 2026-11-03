UPDATE M_Attribute
SET Value = 'HU_Quarantine'
WHERE Value = 'HU_Locked';








UPDATE M_AttributeValue
SET Value = 'quarantine'
WHERE Value = 'locked'
AND M_Attribute_Id = 540039;





-- 2018-09-18T11:09:32.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Name='Quarantäne',Updated=TO_TIMESTAMP('2018-09-18 11:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540039
;

-- 2018-09-18T11:11:29.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeValue SET Name='Quarantäne',Updated=TO_TIMESTAMP('2018-09-18 11:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_AttributeValue_ID=540019
;

