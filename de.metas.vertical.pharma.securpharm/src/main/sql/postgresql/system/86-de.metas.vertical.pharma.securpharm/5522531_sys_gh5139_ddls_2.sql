-- only one active record allowed
create unique index M_Securpharm_Config_UQ on M_Securpharm_Config(IsActive) where IsActive='Y';


