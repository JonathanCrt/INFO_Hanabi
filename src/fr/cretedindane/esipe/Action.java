
public class Action {
    /**Option aura les valeurs de 1, 2 ou 3
     * Si option == 1, the player give a tip
     * Si option == 2, the player play a card
     * Si optoin == 3, play drop one of his cards */
    private int option;

    public Action(int opt){
        this.option = opt;
    }

    public int getOption(){
        return this.option;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringbBuilder();
        if(this.option == 1){
            sb.append("You can now give a tip. ").append("\n");
        } else if (this.option == 2){
            sb.append("You can now play a card. ").append("\n");
        } else if (this.option == 3){
            sb.append("You can now drop a card. ").append("\n");
        }

    }
}