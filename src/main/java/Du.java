import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

public class Du {
    @Option(name = "-h", metaVar = "Human", usage = "Human friendly output.")
    boolean human;
    @Option(name = "-c", metaVar = "Count", usage = "Total size of the files.")
    boolean count;
    @Option(name = "--si", metaVar = "System International", usage = "Use SI.")
    boolean base;
    @Argument(usage = "Fully qualified path and name of files.", metaVar = "File Paths", handler = StringArrayOptionHandler.class, required = true)
    String[] fileNames;

    void doMain(final String[] arguments) {
        final CmdLineParser parser = new CmdLineParser(this);
        if (arguments.length != 0) {
            try {
                parser.parseArgument(arguments);
            } catch (CmdLineException clEx) {
                System.out.println("ERROR: Unable to parse command-line options: " + clEx);
            }
        } else {
            parser.printUsage(System.err);
            System.exit(0);
        }
    }
    public static void main(final String[] arguments) {
        final Du du = new Du();
        du.doMain(arguments);
        FileInfoKt.printInfo(du.base ? 1000 : 1024, du.human, du.count, du.fileNames);
    }
}
