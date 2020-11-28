
-- not related to #2121:
-- fix the salesRep_id field of the purchase order window
-- the old value was #@AD_User@ which is wrong in two different ways
UPDATE AD_Field SET DefaultValue='@#AD_User_ID@' WHERE AD_Field_ID=3438;
