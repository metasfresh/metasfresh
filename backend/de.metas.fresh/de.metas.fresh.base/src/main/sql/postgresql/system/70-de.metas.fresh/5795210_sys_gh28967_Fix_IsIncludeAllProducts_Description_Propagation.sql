-- gh#28967: Fix missing description on IsIncludeAllProducts process parameters
-- The element translation propagation ran before the process params 543158/543159
-- were created, so the description was never propagated.

-- Re-run propagation (covers all three process params and their _Trl rows)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'en_US');
