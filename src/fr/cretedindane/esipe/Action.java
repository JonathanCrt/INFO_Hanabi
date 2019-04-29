
public class Action {
    /**Option aura les valeurs de 1, 2 ou 3
     * Si option == 1, le joueur donne un indice
     * Si option == 2, le joueur joue une carte
     * Si optoin == 3, le joueur se défausse d'une de ses cartes */
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