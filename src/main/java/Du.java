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
    @Argument(usage="Fully qualified path and name of files.", handler = StringArrayOptionHandler.class)
    String[] fileNames;

    private void doMain(final String[] arguments) throws IOException {
        final CmdLineParser parser = new CmdLineParser(this);
        if (arguments.length != 0) {
            try {
                parser.parseArgument(arguments);
            } catch (CmdLineException clEx) {
                System.out.println("ERROR: Unable to parse command-line options: " + clEx);
            }
        } else {
            parser.printUsage(System.out);
            System.exit(1);
        }
    }
    public static void main(final String[] arguments) {
        final Du du = new Du();
        try {
            du.doMain(arguments);
            SizeManager operator = new SizeManager(du.base ? 1000 : 1024, du.human, du.count, du.fileNames);
            operator.printInfo();
        } catch (IOException ioEx){
            System.out.println("ERROR: I/O Exception encountered: " + ioEx);
        }
    }
}
