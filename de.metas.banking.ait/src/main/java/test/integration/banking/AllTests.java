/**
 * 
 */
package test.integration.banking;

/*
 * #%L
 * de.metas.banking.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import de.metas.adempiere.ait.test.IntegrationTestListeners;
import de.metas.adempiere.ait.test.IntegrationTestSuite;

/**
 * @author tsa
 * 
 */
@RunWith(IntegrationTestSuite.class)
@IntegrationTestListeners({
	test.integration.banking.PaymentTestListener.class
})
@SuiteClasses({
		de.schaeffer.pay.service.impl.TeleCashBLIntT.class,
		test.integration.banking.PaymentTestDriver.class
})
public class AllTests
{
}
