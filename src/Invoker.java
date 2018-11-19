public class Invoker {
    Command[] commands;

    Invoker(){
        commands = new Command[4];

        Command no = new NoCommand();
        for (int i = 0; i < commands.length; i++){
            commands[i] = no;
        }
    }

    public void setCommand(int i, Command com){
        commands[i] = com;
    }

    public void ButtonWasPressed(int i){
        commands[i].execute();
    }

    public void printCommands(){
        for (Command command : commands){
            System.out.println(command);
        }
    }
}
