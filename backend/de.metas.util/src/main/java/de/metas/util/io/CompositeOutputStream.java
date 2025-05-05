package de.metas.util.io;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Groups multiple output streams and write to all of them in parallel.
 */
public class CompositeOutputStream extends OutputStream
{
	private final ImmutableList<OutputStream> outputStreams;

	public CompositeOutputStream(@NonNull final ImmutableList<OutputStream> outputStreams)
	{
		this.outputStreams = Check.assumeNotEmpty(outputStreams, "outputStreams cannot be empty");
	}

	public static OutputStream ofList(final List<OutputStream> outputStreams)
	{
		if (outputStreams.isEmpty())
		{
			throw Check.mkEx("outputStreams must not be empty");
		}
		else if (outputStreams.size() == 1)
		{
			return outputStreams.get(0);
		}
		else
		{
			return new CompositeOutputStream(ImmutableList.copyOf(outputStreams));
		}
	}

	/**
	 * Convenient method to create an output stream which writes to all given files of the given directory
	 */
	public static OutputStream ofFilenames(@NonNull final Path directory, final @NonNull Set<String> filenames) throws IOException
	{
		if (filenames.isEmpty())
		{
			throw Check.mkEx("outputStreams must not be empty");
		}
		else if (filenames.size() == 1)
		{
			final File file = new File(directory.toFile(), filenames.iterator().next());
			return Files.newOutputStream(file.toPath());
		}
		else
		{
			final ImmutableList.Builder<OutputStream> outputStreams = ImmutableList.builder();
			for (final String filename : filenames)
			{
				final File file = new File(directory.toFile(), filename);
				outputStreams.add(Files.newOutputStream(file.toPath()));
			}

			return new CompositeOutputStream(outputStreams.build());
		}
	}

	@Override
	public void write(final byte @NonNull [] b) throws IOException
	{
		for (final OutputStream os : outputStreams)
		{
			os.write(b);
		}
	}

	@Override
	public void write(final byte @NonNull [] b, final int off, final int len) throws IOException
	{
		for (final OutputStream os : outputStreams)
		{
			os.write(b, off, len);
		}
	}

	@Override
	public void write(final int b) throws IOException
	{
		for (final OutputStream os : outputStreams)
		{
			os.write(b);
		}
	}

	@Override
	public void flush() throws IOException
	{
		for (final OutputStream os : outputStreams)
		{
			os.flush();
		}
	}

	@Override
	public void close() throws IOException
	{
		for (final OutputStream os : outputStreams)
		{
			os.close();
		}
	}
}
