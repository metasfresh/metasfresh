--
-- remove C_Invoice_Candidate.Ref_PackingMaterial_InOutLine
--
-- 2018-04-04T12:51:33.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558625
;

-- 2018-04-04T12:51:33.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558625
;

ALTER TABLE C_Invoice_Candidate DROP COLUMN Ref_PackingMaterial_InOutLine_ID;
