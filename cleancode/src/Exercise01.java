class Program
{
    public static void main(String[] args)
    {
        SqlRunner sqlRunner = new SqlRunner();

        Runnable write = () -> System.out.println("Writing");
        Runnable read = () -> System.out.println("Reading");

        //sqlRunner.runSql(SqlRunner::write);
        sqlRunner.runSql(write);

        System.out.println("...");

        //sqlRunner.runSql(SqlRunner::read);
        sqlRunner.runSql(read);
    }
}

class SqlRunner
{
    public void runSql(Runnable sqlCmd)
    {
        loadDriver();
        connectDb();

        sqlCmd.run();

        closeDb();
    }

    void loadDriver()
    {
        System.out.println("Loading driver");
    }

    void connectDb()
    {
        System.out.println("Connecting");
    }
    void closeDb()
    {
        System.out.println("Closing");
    }

    public static void write() {
        System.out.println("Writing");
    }

    public static void read() {
        System.out.println("Reading");
    }
};

