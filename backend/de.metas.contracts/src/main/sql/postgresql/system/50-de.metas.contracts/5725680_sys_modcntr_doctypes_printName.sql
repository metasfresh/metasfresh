-- Run mode: WEBUI

-- 2024-06-05T08:18:20.575Z
UPDATE C_DocType SET PrintName='Definitive Schlusszahlung',Updated=TO_TIMESTAMP('2024-06-05 11:18:20.575','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541106
;

-- 2024-06-05T08:18:20.577Z
UPDATE C_DocType_Trl trl SET PrintName='Definitive Schlusszahlung' WHERE C_DocType_ID=541106 AND (IsTranslated='N' OR AD_Language='de_DE' OR AD_Language='de_CH')
;

-- 2024-06-05T08:20:35.915Z
UPDATE C_DocType SET PrintName='Schlussabrechnung',Updated=TO_TIMESTAMP('2024-06-05 11:20:35.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541122
;

-- 2024-06-05T08:20:35.917Z
UPDATE C_DocType_Trl trl SET PrintName='Schlussabrechnung' WHERE C_DocType_ID=541122 AND (IsTranslated='N' OR AD_Language='de_DE' OR AD_Language='de_CH')
;

-- 2024-06-05T08:20:44.154Z
UPDATE C_DocType SET PrintName='Definitive Schlussabrechnung',Updated=TO_TIMESTAMP('2024-06-05 11:20:44.154','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;

-- 2024-06-05T08:20:44.156Z
UPDATE C_DocType_Trl trl SET PrintName='Definitive Schlussabrechnung' WHERE C_DocType_ID=541123 AND (IsTranslated='N' OR AD_Language='de_DE' OR AD_Language='de_CH')
;

