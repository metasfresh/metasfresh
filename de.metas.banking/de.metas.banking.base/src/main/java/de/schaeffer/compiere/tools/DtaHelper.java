package de.schaeffer.compiere.tools;

/*
 * #%L
 * de.metas.banking.base
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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.compiere.model.MDirectDebit;
import org.compiere.util.Env;
import org.jdtaus.banking.AlphaNumericText27;
import org.jdtaus.banking.Bankleitzahl;
import org.jdtaus.banking.Kontonummer;
import org.jdtaus.banking.Textschluessel;
import org.jdtaus.banking.dtaus.Header;
import org.jdtaus.banking.dtaus.LogicalFile;
import org.jdtaus.banking.dtaus.LogicalFileType;
import org.jdtaus.banking.dtaus.PhysicalFile;
import org.jdtaus.banking.dtaus.PhysicalFileFactory;
import org.jdtaus.banking.dtaus.Transaction;
import org.jdtaus.core.container.ContainerFactory;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.banking.exception.BankingException;

public class DtaHelper {

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(DtaHelper.class);

	public static Transaction buildTransaction(Textschluessel textschluessel,
			final String vz1, final String vz2, String currencyIso,
			BigDecimal amount, final String nameFrom, BigInteger accountFrom,
			BigInteger routingNoFrom, final String nameTarget,
			BigInteger accountTarget, BigInteger routingNoTarget) {

		final Transaction transaction = new Transaction();

		final AlphaNumericText27 nameFromA27;
		final AlphaNumericText27[] descA27 = new AlphaNumericText27[2];
		final AlphaNumericText27 mameTargetA27;

		try {
			descA27[0] = AlphaNumericText27.parse(toAlphaNumericText27(vz1));
			descA27[1] = AlphaNumericText27.parse(toAlphaNumericText27(vz2));

			mameTargetA27 = AlphaNumericText27
					.parse(toAlphaNumericText27(nameTarget));
			nameFromA27 = AlphaNumericText27
					.valueOf(toAlphaNumericText27(nameFrom));

		} catch (ParseException e) {
			// TODO -> AD_Message
			throw new BankingException(
					"Zeichenkette kann nicht verarbeitet werden: "
							+ e.getMessage(), e);
		}

		transaction.setDescriptions(descA27);
		transaction.setType(textschluessel);
		transaction.setCurrency(Currency.getInstance("EUR"));
		transaction.setAmount(amount.multiply(Env.ONEHUNDRED).toBigInteger());

		transaction.setExecutiveName(nameFromA27);
		transaction.setExecutiveAccount(Kontonummer.valueOf(accountFrom));
		transaction.setExecutiveBank(Bankleitzahl.valueOf(routingNoFrom));

		transaction.setTargetName(mameTargetA27);
		
		if (Kontonummer.checkKontonummer(accountTarget)) {
			transaction.setTargetAccount(Kontonummer.valueOf(accountTarget));
		} else {
			// TODO -> AD_Message
			throw new BankingException("Ungueltige Empfaenger-Konto-Nr: "
					+ accountTarget);
		}
		if (Bankleitzahl.checkBankleitzahl(routingNoTarget)) {
			transaction.setTargetBank(Bankleitzahl.valueOf(routingNoTarget));
		} else {
			// TODO -> AD_Message
			throw new BankingException("Ungueltige Empfaenger-BLZ: "
					+ routingNoTarget);
		}
		return transaction;
	}

	private static String toAlphaNumericText27(String nameFrom) {

		String result = nameFrom.toUpperCase();

		// replacing umlauts according to
		// http://www.unicode.org/charts/PDF/U0080.pdf
		// note: german umlauts are OK
		result = result.replaceAll(
				"[\u00C0\u00C1\u00C2\u00C3\u00C5\u00C6]", "A");
		result = result.replaceAll("[\u00D2\u00D3\u00D4\u00D5]", "O");
		result = result.replaceAll("[\u00C8\u00C9\u00CA\u00CB]", "E");
		result = result.replaceAll("[\u00D9\u00DA\u00DB]", "U");

		result = result.replaceAll("[^A-Z0-9\\.\\+\\*$ ,&,-/%\u00C4\u00D6\u00DC\u00DF]", "");

		if (result.length() > 27) {
			result = result.substring(0, 27);
		}

		return result;
	}

	// FIXME: move it to swingui or introduce IClientUI.chooseFile(...)
	public static void createFile(List<Transaction> transactionList,
			MDirectDebit directDebit, boolean remittance, String trxName)
			throws IOException {

		JFileChooser fc = new JFileChooser();

		// fc.putClientxProperty("FileChooser.useShellFolder", Boolean.FALSE);
		int returnVal = fc.showSaveDialog(new JFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			final File outputFile = fc.getSelectedFile();

			createFile(transactionList, directDebit, remittance, outputFile);
		}

	}

	public static void createFile(List<Transaction> transactionList,
			MDirectDebit directDebit, boolean remittance, final File outputFile)
			throws IOException, FileNotFoundException {

		final String filePath = outputFile.getParent();
		final String fileName = outputFile.getName();

		final File tmpFile = File.createTempFile(fileName, ".dta", new File(
				filePath));

		final PhysicalFileFactory physicalFileFactory = (PhysicalFileFactory) ContainerFactory
				.getContainer().getObject(PhysicalFileFactory.class.getName());

		final PhysicalFile pFile = physicalFileFactory.createPhysicalFile(
				tmpFile, PhysicalFileFactory.FORMAT_DISK);

		final List<LogicalFile> logicalFileList = new ArrayList<LogicalFile>();

		for (Transaction transaction : transactionList) {

			LogicalFile logicalFile = null;

			for (LogicalFile lFile : logicalFileList) {

				if (lFile.getHeader().getAccount().equals(
						transaction.getExecutiveAccount())
						&& lFile.getHeader().getBank().equals(
								transaction.getExecutiveBank())
						&& lFile.getHeader().getCustomer().toString().trim()
								.equals(
										transaction.getExecutiveName()
												.toString().trim())
						&& lFile.getHeader().getCurrency().equals(
								transaction.getCurrency())) {

					logicalFile = lFile;
					break;
				}
			}

			if (logicalFile == null) {

				final Header header = new Header();

				// Eigene Bankdaten eintragen
				header.setAccount(transaction.getExecutiveAccount());
				header.setBank(transaction.getExecutiveBank());
				header.setCreateDate(new java.util.Date());
				header.setCurrency(transaction.getCurrency());
				header.setCustomer(transaction.getExecutiveName());
				if (remittance) {
					header.setType(LogicalFileType.GK);
				} else {
					header.setType(LogicalFileType.LK);
				}
				logicalFile = pFile.addLogicalFile(header);
				logicalFileList.add(logicalFile);
			} else {

			}

			logicalFile.addTransaction(transaction);
		}

		pFile.commit();

		final String path = tmpFile.getAbsolutePath();

		String buf = "";

		BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(path)));

		while (input.ready()) {
			buf += input.readLine();
		}
		input.close();
		if (directDebit != null) {
			directDebit.setDtafile(buf);
		}
		new File(path).renameTo(new File(filePath.toString() + "\\" + fileName
				+ ".dta"));
	}
}
