
 -- task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it and return it to how it was
 -- but check out 5434162_mig_09533_M_PriceList_Version_set_processed_Y_for_mt.sql in de.metas.materialtracking
UPDATE M_PriceList_Version
SET Processed='N'; -- restore the former state
