-- 2021-07-13T11:03:28.930Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1 from C_BPartner_AlbertaRole where C_BPartner_AlbertaRole.C_BPartner_ID=C_BPartner.C_BPartner_ID and (C_BPartner_AlbertaRole.albertarole=''PD'' or C_BPartner_AlbertaRole.albertarole=''GP''))',Updated=TO_TIMESTAMP('2021-07-13 13:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540546
;

