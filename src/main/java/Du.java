import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import java.io.IOException;

public class Du {
    @Option(name = "-h", usage = "Human friendly output.")
    boolean human;
    @Option(name = "-c", usage = "Total size of the files.")
    boolean count;
    @Option(name = "--si", usage = "Use SI.")
    boolean base;
    @Argument(usage="Fully qualified path and name of files.", handler = StringArrayOptionHandler.class, required = true)
    String[] fileName;

    private void doMain(final String[] arguments) throws IOException {
        final CmdLineParser parser = new CmdLineParser(this);
        if (arguments.length < 1) {
            parser.printUsage(System.out);
            System.exit(1);
        }
        try {
            parser.parseArgument(arguments);
        }
        catch (CmdLineException clEx) {
            System.out.println("ERROR: Unable to parse command-line options: " + clEx);
        }
    }
    public static void main(final String[] arguments) {
        final Du instance = new Du();
        try {
            instance.doMain(arguments);
            SizeManager operator = new SizeManager(instance.base ? 1000 : 1024, instance.human, instance.count, instance.fileName);
            operator.printInfo();
        }
        catch (IOException ioEx) {
            System.out.println("ERROR: I/O Exception encountered: " + ioEx);
        }
    }
}
