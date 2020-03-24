-- note that our postgres 9.5. does not yet have "ADD COLUMN IF NOT EXISTS"
DO $$
BEGIN
-- 2019-01-25T12:25:13.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ ALTER TABLE public.AD_Table_Process ADD COLUMN AD_Tab_ID NUMERIC(10)
;
EXCEPTION WHEN SQLSTATE '42701' THEN
    RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;

DO $$
BEGIN

-- 2019-01-25T12:35:15.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ ALTER TABLE public.AD_Table_Process ADD COLUMN WEBUI_DocumentAction CHAR(1) DEFAULT 'Y' CHECK (WEBUI_DocumentAction IN ('Y','N')) NOT NULL
;
EXCEPTION WHEN SQLSTATE '42701' THEN
    RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;

DO $$
BEGIN
-- 2019-01-25T12:35:35.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ ALTER TABLE public.AD_Table_Process ADD COLUMN WEBUI_ViewAction CHAR(1) DEFAULT 'Y' CHECK (WEBUI_ViewAction IN ('Y','N')) NOT NULL
;
EXCEPTION WHEN SQLSTATE '42701' THEN
    RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;

DO $$
BEGIN
-- 2019-01-25T12:35:54.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ ALTER TABLE public.AD_Table_Process ADD COLUMN WEBUI_IncludedTabTopAction CHAR(1) DEFAULT 'N' CHECK (WEBUI_IncludedTabTopAction IN ('Y','N')) NOT NULL
;
EXCEPTION WHEN SQLSTATE '42701' THEN
    RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;


DO $$
BEGIN

alter table AD_Table_Process rename WEBUI_QuickAction to WEBUI_ViewQuickAction;

EXCEPTION WHEN SQLSTATE '42703' THEN
    RAISE NOTICE 'column doesn''t exist, so we assume it was already renamed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;
DO $$
BEGIN

alter table AD_Table_Process rename WEBUI_QuickAction_Default to WEBUI_ViewQuickAction_Default;

EXCEPTION WHEN SQLSTATE '42703' THEN
    RAISE NOTICE 'column doesn''t exist, so we assume it was already renamed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;
