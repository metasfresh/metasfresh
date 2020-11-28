-- 2019-03-11T12:47:56.686
-- #298 changing anz. stellen
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=556919
;

-- 2019-03-11T12:48:20.765
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-03-11 12:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556922
;

-- 2019-03-11T12:48:30.820
-- #298 changing anz. stellen
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=560339
;

-- 2019-03-11T12:48:30.828
-- #298 changing anz. stellen
DELETE FROM AD_Field WHERE AD_Field_ID=560339
;

-- 2019-03-11T12:48:30.876
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=620507
;






DELETE FROM AD_ImpFormat_Row WHERE AD_Column_ID = 557239;





-- 2019-03-11T12:56:35.878
-- #298 changing anz. stellen
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=557239
;

-- 2019-03-11T12:56:35.889
-- #298 changing anz. stellen
DELETE FROM AD_Column WHERE AD_Column_ID=557239
;

