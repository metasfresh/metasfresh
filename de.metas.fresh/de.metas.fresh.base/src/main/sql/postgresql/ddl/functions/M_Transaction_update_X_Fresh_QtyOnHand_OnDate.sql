drop table if exists X_fresh_QtyOnHand_OnDate;
drop table if exists "de.metas.fresh".X_fresh_QtyOnHand_OnDate;

create table "de.metas.fresh".X_fresh_QtyOnHand_OnDate (
	MovementDate	date NOT NULL,
	M_Product_ID NUMERIC(10) NOT NULL,
	M_AttributeSetInstance_ID NUMERIC(10) NOT NULL,
	Qty NUMERIC NOT NULL DEFAULT 0,
	PRIMARY KEY (MovementDate, M_Product_ID, M_AttributeSetInstance_ID)
);

DROP FUNCTION IF EXISTS M_Transaction_update_X_fresh_QtyOnHand_OnDate();
DROP FUNCTION IF EXISTS "de.metas.fresh".M_Transaction_update_X_fresh_QtyOnHand_OnDate();

CREATE OR REPLACE FUNCTION "de.metas.fresh".m_transaction_update_x_fresh_qtyonhand_ondate()
  RETURNS trigger AS
$BODY$
DECLARE
	v_M_Product_ID				NUMERIC(10);
	v_M_AttributeSetInstance_ID	NUMERIC(10);
	v_Date						DATE;
	v_Qty						NUMERIC;
	v_Record					RECORD;
BEGIN
	IF (TG_OP='INSERT') THEN
		v_M_Product_ID 				:= NEW.M_Product_ID;
		v_M_AttributeSetInstance_ID	:= NEW.M_AttributeSetInstance_ID;
		v_Date 						:= NEW.MovementDate;
		v_Qty 						:= NEW.MovementQty;
		v_Record 					:= NEW;
	ELSIF (TG_OP='DELETE') THEN
		v_M_Product_ID 				:= OLD.M_Product_ID;
		v_M_AttributeSetInstance_ID	:= OLD.M_AttributeSetInstance_ID;
		v_Date 						:= OLD.MovementDate;
		v_Qty 						:= OLD.MovementQty * -1;
		v_Record 					:= OLD;
	ELSE
		RAISE EXCEPTION 'Trigger operation not supported';
	END IF;

	LOOP
		-- first try to update the key
		UPDATE "de.metas.fresh".X_fresh_QtyOnHand_OnDate t SET Qty=Qty+v_Qty 
		WHERE t.M_Product_ID=v_M_Product_ID
				AND t.M_AttributeSetInstance_ID=v_M_AttributeSetInstance_ID
				AND t.MovementDate=v_Date;
		IF found THEN
			RETURN v_Record;
		END IF;
		-- not there, so try to insert the key
		-- if someone else inserts the same key concurrently,
		-- we could get a unique-key failure
		BEGIN
			INSERT INTO "de.metas.fresh".X_fresh_QtyOnHand_OnDate(M_Product_ID, M_AttributeSetInstance_ID, MovementDate, Qty) VALUES (v_M_Product_ID, v_M_AttributeSetInstance_ID, v_Date, v_Qty);
			RETURN v_Record;
		EXCEPTION WHEN unique_violation THEN
			-- do nothing, and loop to try the UPDATE again
		END;
	END LOOP;		
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "de.metas.fresh".m_transaction_update_x_fresh_qtyonhand_ondate()
  OWNER TO adempiere;
COMMENT ON FUNCTION "de.metas.fresh".m_transaction_update_x_fresh_qtyonhand_ondate() IS 'the function is VOLATILE because it is called from a trigger function and to avoid the error "ERROR: UPDATE is not allowed in a non-volatile function';


DROP TRIGGER IF EXISTS M_Transaction_update_X_fresh_QtyOnHand_OnDate_INSERT_trigger ON M_Transaction;
CREATE TRIGGER M_Transaction_update_X_fresh_QtyOnHand_OnDate_INSERT_trigger
	AFTER INSERT
	ON M_Transaction
	FOR EACH ROW
	WHEN (NEW.MovementType IN ('C-', 'C+', 'V-', 'V+'))
	EXECUTE PROCEDURE "de.metas.fresh".M_Transaction_update_X_fresh_QtyOnHand_OnDate();
--
DROP TRIGGER IF EXISTS M_Transaction_update_X_fresh_QtyOnHand_OnDate_DELETE_trigger ON M_Transaction;
CREATE TRIGGER M_Transaction_update_X_fresh_QtyOnHand_OnDate_DELETE_trigger
	AFTER DELETE
	ON M_Transaction
	FOR EACH ROW
	WHEN (OLD.MovementType IN ('C-', 'C+', 'V-', 'V+'))
	EXECUTE PROCEDURE "de.metas.fresh".M_Transaction_update_X_fresh_QtyOnHand_OnDate();
	
	
DROP FUNCTION IF EXISTS M_Transaction_update_X_fresh_QtyOnHand_OnDate();	
DROP TRIGGER IF EXISTS M_Transaction_update_X_fresh_QtyOnHand_OnDate_INSERT_trigger ON M_Transaction;
DROP TRIGGER IF EXISTS M_Transaction_update_X_fresh_QtyOnHand_OnDate_DELETE_trigger ON M_Transaction;
