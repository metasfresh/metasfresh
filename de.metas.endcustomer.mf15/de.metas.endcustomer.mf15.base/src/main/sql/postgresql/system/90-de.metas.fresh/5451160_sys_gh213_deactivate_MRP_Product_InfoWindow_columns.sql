
-- https://github.com/metasfresh/metasfresh/issues/213
UPDATE AD_InfoColumn SET IsActive='N', Updated=now(), UpdatedBy=99
WHERE AD_InfoColumn_ID IN
(
540128 -- Zählbestand
,540129 --Zusagbar Zählbestand
,540126 --Materialentnahme
,540127 --MRP Menge
);
