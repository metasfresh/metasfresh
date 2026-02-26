package de.metas.bpartner;

import java.util.Random;

public class RandomGLNGenerator
{
	private final Random random = new Random();

	public GLN next()
	{
		StringBuilder gln = new StringBuilder();

		// Step 1: Generate 12 random digits
		for (int i = 0; i < 12; i++)
		{
			gln.append(random.nextInt(10)); // Append a random digit (0-9)
		}

		// Step 2: Calculate the checksum digit
		int checksum = calculateChecksum(gln.toString());

		// Step 3: Append the checksum to form the complete GLN
		gln.append(checksum);

		return GLN.ofString(gln.toString());
	}

	private static int calculateChecksum(String gln)
	{
		int sum = 0;

		// Apply the EAN-13 checksum algorithm
		for (int i = 0; i < gln.length(); i++)
		{
			int digit = Character.getNumericValue(gln.charAt(i));
			//noinspection PointlessArithmeticExpression
			sum += (i % 2 == 0) ? digit * 1 : digit * 3; // Odd-position digits multiplied by 1, even by 3
		}

		// Calculate the difference to the next multiple of 10
		int nearestMultipleOfTen = (int)(Math.ceil(sum / 10.0) * 10);
		return nearestMultipleOfTen - sum;
	}

	public static void main(String[] args)
	{
		// Example usage
		final RandomGLNGenerator generator = new RandomGLNGenerator();
		for(int i = 1; i <= 10; i++)
		{
			final GLN randomGLN = generator.next();
			System.out.println("Random GLN: " + randomGLN);
		}
	}

}
