-- 17.06.2016 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
C_BPartner.C_BPartner_ID in (select C_BPartnerRelation_ID from C_BP_Relation r where r.C_BPartner_ID
= 
(CASE WHEN  @C_BPartner_Override_ID@ > 0 then @C_BPartner_Override_ID@
ELSE  @C_BPartner_ID@
END) 
and r.IsHandOverLocation =''Y'')',Updated=TO_TIMESTAMP('2016-06-17 14:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540336
;

-- 17.06.2016 14:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
C_BPartner.C_BPartner_ID in (select C_BPartnerRelation_ID from C_BP_Relation r where r.C_BPartner_ID
= 
(CASE WHEN  @C_BPartner_Override_ID/-1@ > 0 then @C_BPartner_Override_ID@
ELSE  @C_BPartner_ID@
END) 
and r.IsHandOverLocation =''Y'')',Updated=TO_TIMESTAMP('2016-06-17 14:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540336
;

-- 17.06.2016 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.IsHandoverLocation = ''Y''  AND (
C_BPartner_Location.C_BPartner_ID = 
(
CASE WHEN  @C_BPartner_Override_ID@ > 0 then @C_BPartner_Override_ID@
ELSE @C_BPartner_ID@
END
)
)
',Updated=TO_TIMESTAMP('2016-06-17 15:46:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

-- 17.06.2016 15:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.IsHandoverLocation = ''Y''  AND (
C_BPartner_Location.C_BPartner_ID = 
(
CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
ELSE @HandOver_Partner_ID@
END
)
)
',Updated=TO_TIMESTAMP('2016-06-17 15:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

-- 17.06.2016 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
(
C_BPartner_Location.IsHandoverLocation = ''Y''  AND (
C_BPartner_Location.C_BPartner_ID = 
(
CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
ELSE @HandOver_Partner_ID@
END
)
)
)
OR
(
	EXISTS (
	select 1 from C_BP_Relation r 
		where r.isHandoverLocation = ''Y'' and
		r.C_BPartnerRelation_Location_ID = C_BPartner_Location.C_BPartner_ID and
		r.C_BPartner_ID = 
		(CASE WHEN  @C_BPartner_Override_ID@ > 0 then @C_BPartner_Override_ID@
		ELSE  @C_BPartner_ID@
		END)		
		) 
)
',Updated=TO_TIMESTAMP('2016-06-17 16:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

-- 17.06.2016 16:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
(
C_BPartner_Location.IsHandoverLocation = ''Y''  AND (
C_BPartner_Location.C_BPartner_ID = 
(
CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
ELSE @HandOver_Partner_ID@
END
)
)
)
OR
(
	EXISTS (
	select 1 from C_BP_Relation r 
		where r.isHandoverLocation = ''Y'' and
		r.C_BPartnerRelation_Location_ID = C_BPartner_Location.C_BPartner_ID and
		r.C_BPartner_ID = 
		(CASE WHEN  @C_BPartner_Override_ID@ > 0 then @C_BPartner_Override_ID@
		ELSE  @C_BPartner_ID@
		END)

		AND
		
		r.C_BPartnerRelation_ID =
		
			(
			CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
			ELSE @HandOver_Partner_ID@
			END
			)
		
) 
)
',Updated=TO_TIMESTAMP('2016-06-17 16:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

-- 17.06.2016 16:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
( testtest
C_BPartner_Location.IsHandoverLocation = ''Y''  AND (
C_BPartner_Location.C_BPartner_ID = 
(
CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
ELSE @HandOver_Partner_ID@
END
)
)
)
OR
(
	EXISTS (
	select 1 from C_BP_Relation r 
		where r.isHandoverLocation = ''Y'' and
		r.C_BPartnerRelation_Location_ID = C_BPartner_Location.C_BPartner_ID and
		r.C_BPartner_ID = 
		(CASE WHEN  @C_BPartner_Override_ID@ > 0 then @C_BPartner_Override_ID@
		ELSE  @C_BPartner_ID@
		END)

		AND
		
		r.C_BPartnerRelation_ID =
		
			(
			CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
			ELSE @HandOver_Partner_ID@
			END
			)
		
) 
)
',Updated=TO_TIMESTAMP('2016-06-17 16:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

-- 17.06.2016 16:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
(
C_BPartner_Location.IsHandoverLocation = ''Y''  AND (
C_BPartner_Location.C_BPartner_ID = 
(
CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
ELSE @HandOver_Partner_ID@
END
)
)
)
OR
(
	EXISTS (
	select 1 from C_BP_Relation r 
		where r.isHandoverLocation = ''Y'' and
		r.C_BPartnerRelation_Location_ID = C_BPartner_Location.C_BPartner_ID and
		r.C_BPartner_ID = 
		(CASE WHEN  @C_BPartner_Override_ID@ > 0 then @C_BPartner_Override_ID@
		ELSE  @C_BPartner_ID@
		END)

		AND
		
		r.C_BPartnerRelation_ID =
		
			(
			CASE WHEN  @HandOver_Partner_Override_ID@ > 0 then @HandOver_Partner_Override_ID@
			ELSE @HandOver_Partner_ID@
			END
			)
		
) 
)
',Updated=TO_TIMESTAMP('2016-06-17 16:12:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540335
;

