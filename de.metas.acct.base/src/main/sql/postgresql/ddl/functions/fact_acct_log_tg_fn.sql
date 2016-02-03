-- drop table if exists Fact_Acct_Log;
create table Fact_Acct_Log (
	Fact_Acct_ID numeric not null
	, Action char(1) not null
	, ProcessingTag varchar(60)
	--
	, C_ElementValue_ID numeric not null
	, C_AcctSchema_ID numeric not null
	, C_Period_ID numeric not null
	, DateAcct timestamp without time zone not null
	, PostingType char(1) not null
	--
	, AmtAcctDr numeric not null
	, AmtAcctCr numeric not null
	, Qty numeric not null
	--
	-- Standard columns
	, ad_client_id numeric(10,0) NOT NULL
	, ad_org_id numeric(10,0) NOT NULL
	, isactive character(1) NOT NULL DEFAULT 'Y'::bpchar
	, created timestamp with time zone NOT NULL DEFAULT now()
	, createdby numeric(10,0) NOT NULL
	, updated timestamp with time zone NOT NULL DEFAULT now()
	, updatedby numeric(10,0) NOT NULL
);
--
alter table Fact_Acct_Log SET WITH OIDS;

--
-- Drop existing triggers
drop trigger if exists fact_acct_log_tg ON Fact_Acct;
drop trigger if exists fact_acct_log_insert_or_delete_tg ON Fact_Acct;
drop trigger if exists fact_acct_log_update_tg ON Fact_Acct;
drop function if exists fact_acct_log_tg_fn();

--
-- Trigger function
create or replace function fact_acct_log_tg_fn()
returns trigger as
$BODY$
begin
	-- NOTE to developer: if you are adding a new column here, please make sure you also check the fact_acct_log_update_tg's WHEN(...) clause
	
	if (TG_OP = 'UPDATE' or TG_OP = 'DELETE') then
		insert into Fact_Acct_Log
		(
			Fact_Acct_ID, Action
			, C_ElementValue_ID, C_AcctSchema_ID
			, C_Period_ID
			, DateAcct
			, PostingType
			--
			, AmtAcctDr, AmtAcctCr
			, Qty
			--
			, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy
		)
		values
		(
			OLD.Fact_Acct_ID, 'D'
			, OLD.Account_ID, OLD.C_AcctSchema_ID
			, OLD.C_Period_ID
			, OLD.DateAcct
			, OLD.PostingType
			--
			, OLD.AmtAcctDr, OLD.AmtAcctCr
			, OLD.Qty
			--
			, OLD.AD_Client_ID, OLD.AD_Org_ID, OLD.IsActive, OLD.Created, OLD.CreatedBy, OLD.Updated, OLD.UpdatedBy
		);
	end if;
	
	if (TG_OP = 'INSERT' or TG_OP = 'UPDATE') then
		insert into Fact_Acct_Log
		(
			Fact_Acct_ID, Action
			, C_ElementValue_ID, C_AcctSchema_ID
			, C_Period_ID
			, DateAcct
			, PostingType
			--
			, AmtAcctDr, AmtAcctCr
			, Qty
			--
			, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy
		)
		values
		(
			NEW.Fact_Acct_ID, 'I'
			, NEW.Account_ID, NEW.C_AcctSchema_ID
			, NEW.C_Period_ID
			, NEW.DateAcct
			, NEW.PostingType
			--
			, NEW.AmtAcctDr, NEW.AmtAcctCr
			, NEW.Qty
			--
			, NEW.AD_Client_ID, NEW.AD_Org_ID, NEW.IsActive, NEW.Created, NEW.CreatedBy, NEW.Updated, NEW.UpdatedBy
		);
	end if;

	return NEW;
end;
$BODY$
LANGUAGE plpgsql
;

--
-- Bind the trigger function to Fact_Acct table's events:
create trigger fact_acct_log_insert_or_delete_tg
	AFTER INSERT OR DELETE
	ON Fact_Acct
	FOR EACH ROW
	EXECUTE PROCEDURE fact_acct_log_tg_fn()
;
create trigger fact_acct_log_update_tg
	AFTER UPDATE
	ON Fact_Acct
	FOR EACH ROW
	WHEN (
		OLD.Fact_Acct_ID IS DISTINCT FROM NEW.Fact_Acct_ID
		or OLD.AD_Client_ID IS DISTINCT FROM NEW.AD_Client_ID
		or OLD.AD_Org_ID IS DISTINCT FROM NEW.AD_Org_ID
		or OLD.Account_ID IS DISTINCT FROM NEW.Account_ID
		or OLD.C_AcctSchema_ID IS DISTINCT FROM NEW.C_AcctSchema_ID
		or OLD.C_Period_ID IS DISTINCT FROM NEW.C_Period_ID
		or OLD.DateAcct IS DISTINCT FROM NEW.DateAcct
		or OLD.PostingType IS DISTINCT FROM NEW.PostingType
		or OLD.AmtAcctDr IS DISTINCT FROM NEW.AmtAcctDr
		or OLD.AmtAcctCr IS DISTINCT FROM NEW.AmtAcctCr
		or OLD.Qty IS DISTINCT FROM NEW.Qty
	)
	EXECUTE PROCEDURE fact_acct_log_tg_fn()
;
