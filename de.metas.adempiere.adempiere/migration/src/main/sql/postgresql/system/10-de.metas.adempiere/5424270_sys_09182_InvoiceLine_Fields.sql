-- 21.08.2015 15:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=147,Updated=TO_TIMESTAMP('2015-08-21 15:36:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552628
;

-- 21.08.2015 15:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540305,'C_OrderLine.C_Order_ID=@C_Order_ID@',TO_TIMESTAMP('2015-08-21 15:39:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','C_OrderLine_Of_Same_Order_And_Product','S',TO_TIMESTAMP('2015-08-21 15:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 21.08.2015 15:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_OrderLine.C_Order_ID=@C_Order_ID@ AND ( @M_Product_ID@<= 0 OR C_OrderLine.M_Product_ID = @M_Product_ID@)',Updated=TO_TIMESTAMP('2015-08-21 15:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;

-- 21.08.2015 15:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540305,Updated=TO_TIMESTAMP('2015-08-21 15:40:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3837
;

-- 21.08.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540306,'C_Order.C_BPartner_ID=@C_BPartner_ID@ AND (@M_Product_ID@<=0 OR EXISTS (SELECT 1 FROM C_OrderLine where C_OrderLine.C_Order_ID = C_Order.C_Order_ID AND C_OrderLine.M_Product_ID = @M_Product_ID@))',TO_TIMESTAMP('2015-08-21 15:43:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','C_Order of C_BPartner_And_Product','S',TO_TIMESTAMP('2015-08-21 15:43:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 21.08.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@ AND (@M_Product_ID@IS NULL OR EXISTS (SELECT 1 FROM C_OrderLine where C_OrderLine.C_Order_ID = C_Order.C_Order_ID AND C_OrderLine.M_Product_ID = @M_Product_ID@))',Updated=TO_TIMESTAMP('2015-08-21 15:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540306
;

-- 21.08.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_OrderLine.C_Order_ID=@C_Order_ID@ AND ( @M_Product_ID@ IS NULL OR C_OrderLine.M_Product_ID = @M_Product_ID@)',Updated=TO_TIMESTAMP('2015-08-21 15:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;

-- 21.08.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540306,Updated=TO_TIMESTAMP('2015-08-21 15:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552628
;

-- 21.08.2015 16:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540307,'M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND (
		(@C_OrderLine_ID@ IS NOT NULL AND EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_OrderLine_ID = @C_OrderLine_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) )
		OR
		(@C_Order_ID@ IS NOT NULL AND EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_Order_ID = @C_Order_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) )
		OR @C_Order_ID@ IS NULL
	)
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	FROM M_ProductPrice
	WHERE M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive=''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <= ''@DateInvoiced@''
			GROUP BY M_PriceList_ID
		)
	)
)
',TO_TIMESTAMP('2015-08-21 16:20:32','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','M_Product (Trx) Invoice_And_Order','S',TO_TIMESTAMP('2015-08-21 16:20:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 21.08.2015 16:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540307,Updated=TO_TIMESTAMP('2015-08-21 16:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3840
;
-- 21.08.2015 16:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@ AND (@M_Product_ID@ IS NULL OR EXISTS (SELECT 1 FROM C_OrderLine where C_OrderLine.C_Order_ID = C_Order.C_Order_ID AND C_OrderLine.M_Product_ID = @M_Product_ID@))',Updated=TO_TIMESTAMP('2015-08-21 16:24:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540306
;

-- 21.08.2015 16:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND (
		(@C_OrderLine_ID@ >0 AND EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_OrderLine_ID = @C_OrderLine_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) )
		OR
		(@C_Order_ID@ >0 AND EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_Order_ID = @C_Order_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) )
		OR @C_Order_ID@ <=0
	)
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	FROM M_ProductPrice
	WHERE M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive=''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <= ''@DateInvoiced@''
			GROUP BY M_PriceList_ID
		)
	)
)
',Updated=TO_TIMESTAMP('2015-08-21 16:34:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540307
;

-- 21.08.2015 16:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@ AND (@M_Product_ID@ <=0 OR EXISTS (SELECT 1 FROM C_OrderLine where C_OrderLine.C_Order_ID = C_Order.C_Order_ID AND C_OrderLine.M_Product_ID = @M_Product_ID@))',Updated=TO_TIMESTAMP('2015-08-21 16:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540306
;

-- 21.08.2015 16:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_OrderLine.C_Order_ID=@C_Order_ID@ AND ( @M_Product_ID@ <=0 OR C_OrderLine.M_Product_ID = @M_Product_ID@)',Updated=TO_TIMESTAMP('2015-08-21 16:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;


-- 21.08.2015 16:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-08-21 16:42:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540307
;

-- 21.08.2015 16:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND (
	CASE
		WHEN @C_OrderLine_ID@ > 0
			THEN
				EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_OrderLine_ID = @C_OrderLine_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) 
		WHEN @C_Order_ID@ > 0
			THEN EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_Order_ID = @C_Order_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) 
		ELSE true
	)
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	FROM M_ProductPrice
	WHERE M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive=''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <= ''@DateInvoiced@''
			GROUP BY M_PriceList_ID
		)
	)
)
',Updated=TO_TIMESTAMP('2015-08-21 16:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540307
;

-- 21.08.2015 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND (
	CASE
		WHEN @C_OrderLine_ID@ > 0
			THEN
				EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_OrderLine_ID = @C_OrderLine_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) 
		WHEN @C_Order_ID@ > 0
			THEN EXISTS(Select 1 from C_OrderLine where C_OrderLine.C_Order_ID = @C_Order_ID@ AND C_OrderLine.M_Product_ID = M_Product.M_Product_ID) 
		ELSE true
		END
	)
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	FROM M_ProductPrice
	WHERE M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive=''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <= ''@DateInvoiced@''
			GROUP BY M_PriceList_ID
		)
	)
)
',Updated=TO_TIMESTAMP('2015-08-21 16:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540307
;

-- 21.08.2015 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_OrderLine.C_Order_ID=@C_Order_ID@ 
AND 
CASE
	WHEN @M_Product_ID@ > 0 
		THEN C_OrderLine.M_Product_ID = @M_Product_ID@
	ELSE true
	END',Updated=TO_TIMESTAMP('2015-08-21 16:57:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;

-- 21.08.2015 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@ 
AND 
CASE
	WHEN @M_Product_ID@ > 0 
		THEN EXISTS (SELECT 1 FROM C_OrderLine where C_OrderLine.C_Order_ID = C_Order.C_Order_ID AND C_OrderLine.M_Product_ID = @M_Product_ID@)
	ELSE TRUE
	END',Updated=TO_TIMESTAMP('2015-08-21 16:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540306
;


-- 21.08.2015 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@ 
AND 
C_Order.C_Order_ID IN
	(
		SELECT C_Order_ID from C_OrderLine where M_Product_ID = @M_Product_ID@ OR @M_Product_ID@ <= 0
	)',Updated=TO_TIMESTAMP('2015-08-21 18:29:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540306
;

-- 21.08.2015 18:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND M_Product.M_Product_ID IN
	(
		Select M_Product_ID from C_OrderLine where C_OrderLine_ID = @C_OrderLine_ID@ OR  C_Order_ID = @C_Order_ID@ OR @C_Order_ID@ <= 0
	)
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	FROM M_ProductPrice
	WHERE M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive=''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <= ''@DateInvoiced@''
			GROUP BY M_PriceList_ID
		)
	)
)
',Updated=TO_TIMESTAMP('2015-08-21 18:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540307
;

-- 21.08.2015 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
C_OrderLine.C_OrderLine_ID IN  (Select C_OrderLine_ID from C_OrderLine where C_Order_ID = @C_Order_ID@ And ( M_Product_ID = @M_Product_ID@ or @M_Product_ID@ <= 0)',Updated=TO_TIMESTAMP('2015-08-21 18:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;

-- 21.08.2015 18:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
C_OrderLine.C_OrderLine_ID IN  (Select C_OrderLine_ID from C_OrderLine where C_Order_ID = @C_Order_ID@ And ( M_Product_ID = @M_Product_ID@ or @M_Product_ID@ <= 0))',Updated=TO_TIMESTAMP('2015-08-21 18:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;

-- 24.08.2015 10:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_OrderLine.C_OrderLine_ID IN  (Select C_OrderLine_ID from C_OrderLine where C_Order_ID = @C_Order_ID@ And ( M_Product_ID = @M_Product_ID@ or @M_Product_ID@ <= 0))',Updated=TO_TIMESTAMP('2015-08-24 10:08:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;

-- 24.08.2015 10:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND M_Product.M_Product_ID IN
	(
		Select M_Product_ID from C_OrderLine where C_OrderLine.C_OrderLine_ID = @C_OrderLine_ID@ OR  C_OrderLine.C_Order_ID = @C_Order_ID@ OR @C_Order_ID@ <= 0
	)
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	FROM M_ProductPrice
	WHERE M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive=''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <= ''@DateInvoiced@''
			GROUP BY M_PriceList_ID
		)
	)
)
',Updated=TO_TIMESTAMP('2015-08-24 10:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540307
;

-- 24.08.2015 10:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='
C_Order.C_BPartner_ID=@C_BPartner_ID@ 
AND 
C_Order.C_Order_ID IN
	(
		SELECT C_Order_ID from C_OrderLine where C_OrderLine.M_Product_ID = @M_Product_ID@ OR @M_Product_ID@ <= 0
	)',Updated=TO_TIMESTAMP('2015-08-24 10:15:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540306
;

-- 24.08.2015 10:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_OrderLine.C_OrderLine_ID IN  (Select C_OrderLine_ID from C_OrderLine where C_OrderLine.C_Order_ID = @C_Order_ID@ And ( C_OrderLine.M_Product_ID = @M_Product_ID@ or @M_Product_ID@ <= 0))',Updated=TO_TIMESTAMP('2015-08-24 10:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540305
;

-- 24.08.2015 10:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND M_Product.M_Product_ID IN
	(
		Select C_OrderLine.M_Product_ID from C_OrderLine where C_OrderLine.C_OrderLine_ID = @C_OrderLine_ID@ OR  (C_OrderLine.C_Order_ID = @C_Order_ID@ AND @C_OrderLine_ID@ <= 0) OR @C_Order_ID@ <= 0
	)
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	FROM M_ProductPrice
	WHERE M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive=''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <= ''@DateInvoiced@''
			GROUP BY M_PriceList_ID
		)
	)
)
',Updated=TO_TIMESTAMP('2015-08-24 10:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540307
;




-- 24.08.2015 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_Order_ID@ > 0 OR @C_OrderLine_ID@ >0
 OR @S_ResourceAssignment_ID@!0 | @C_Charge_ID@!0',Updated=TO_TIMESTAMP('2015-08-24 12:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3840
;

-- 24.08.2015 12:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_Order_ID@>0 | @C_OrderLine_ID@>0 | @S_ResourceAssignment_ID@!0 | @C_Charge_ID@!0',Updated=TO_TIMESTAMP('2015-08-24 12:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3840
;



-- 24.08.2015 13:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_Order_ID@>0',Updated=TO_TIMESTAMP('2015-08-24 13:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3837
;

-- 24.08.2015 13:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540163,Updated=TO_TIMESTAMP('2015-08-24 13:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3840
;

-- 24.08.2015 14:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=147,Updated=TO_TIMESTAMP('2015-08-24 14:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552628
;

-- 24.08.2015 14:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540077,Updated=TO_TIMESTAMP('2015-08-24 14:05:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3837
;

-- 24.08.2015 14:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540307
;

-- 24.08.2015 14:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540306
;

-- 24.08.2015 14:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540305
;

-- 24.08.2015 15:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-08-24 15:09:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556241
;

-- 24.08.2015 15:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-08-24 15:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2987
;

-- 24.08.2015 15:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='((@C_Order_ID@>0 | @C_OrderLine_ID@>0) & @IsOrderLineReadOnly@=N ) | @S_ResourceAssignment_ID@!0 | @C_Charge_ID@!0',Updated=TO_TIMESTAMP('2015-08-24 15:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3840
;

