import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class Main {
    @Option(name = "-h", usage = "Human friendly output.")
    private boolean human;
    @Option(name = "-c", usage = "Total size of the files.")
    private boolean count;
    @Option(name = "--si", usage = "Use SI.")
    private boolean base;
    @Option(name="-f", usage="Fully qualified path and name of files.", required=true)
    private String[] fileName;

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
        final Main instance = new Main();
        try {
            instance.doMain(arguments);
        }
        catch (IOException ioEx) {
            System.out.println("ERROR: I/O Exception encountered: " + ioEx);
        }
    }
}
