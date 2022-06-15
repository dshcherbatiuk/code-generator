package com.cgenerator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class App {
	private App() {
	}

	@Command(name = "git")
	public void git() {

	}

	@Command(name = "fetch", parent = "git")
	public void fetch() {

	}

	@Command(name = "commit", parent = "git")
	public void commit(
			@com.cgenerator.Option(option = "m", longOption = "message", description = "commit message") final String message) {

	}

	public static void main(final String... args) throws Exception {
		// System.out.println("Hello World!!!");

		final Options options = new Options();

		final Option alpha = new Option("a", "alpha", false, "Activate feature alpha");
		options.addOption(alpha);

		final Option config = Option.builder("c")
				.longOpt("config")
				.argName("config")
				.hasArg()
				.required(true)
				.desc("Set config file").build();
		options.addOption(config);

		// define parser
		CommandLine cmd;
		final CommandLineParser parser = new DefaultParser();
		final HelpFormatter helper = new HelpFormatter();

		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("a")) {
				System.out.println("Alpha activated");
			}

			if (cmd.hasOption("c")) {
				final String opt_config = cmd.getOptionValue("config");
				System.out.println("Config set to " + opt_config);
			}
		} catch (final ParseException e) {
			System.out.println(e.getMessage());
			helper.printHelp("Usage:", options);
			System.exit(0);
		}
	}
}
