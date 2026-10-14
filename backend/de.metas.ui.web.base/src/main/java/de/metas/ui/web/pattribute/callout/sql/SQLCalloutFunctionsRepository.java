package de.metas.ui.web.pattribute.callout.sql;

import de.metas.cache.CCache;
import lombok.NonNull;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class SQLCalloutFunctionsRepository
{
	private static final String FUNCTION_NAME_PREFIX = "M_AttributeSetInstance_Callout_";

	private final CCache<Integer, SQLCalloutFunctionList> cache = CCache.<Integer, SQLCalloutFunctionList>builder()
			.initialCapacity(1)
			.build();

	public SQLCalloutFunctionList getAllFunctions()
	{
		return cache.getOrLoad(0, this::retrieveAllFunctions);
	}

	private SQLCalloutFunctionList retrieveAllFunctions()
	{
		final String sql = "SELECT routines.specific_schema, routines.routine_name FROM information_schema.routines "
				+ " WHERE routines.routine_name ILIKE ? "
				+ " ORDER BY routines.routine_name ";
		final List<Object> sqlParams = Collections.singletonList(FUNCTION_NAME_PREFIX + "%");
		return SQLCalloutFunctionList.ofList(DB.retrieveRowsOutOfTrx(sql, sqlParams, this::retrieveFunction));
	}

	private SQLCalloutFunction retrieveFunction(@NonNull final ResultSet rs) throws SQLException
	{
		final String specific_schema = rs.getString("specific_schema");
		final String routine_name = rs.getString("routine_name");
		return SQLCalloutFunction.builder()
				.schema(specific_schema)
				.name(routine_name)
				.build();
	}

}
