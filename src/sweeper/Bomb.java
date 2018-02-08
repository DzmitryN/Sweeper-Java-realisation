package sweeper;


 class Bomb {
     private Matrix bombMap;
     private int totalBomds;

     Bomb(int totalBomds){
         this.totalBomds = totalBomds;
     }

     void start ()
     {
         bombMap = new Matrix(Box.ZERO);
         for (int i = 0; i < totalBomds; i++) {
             placeBomb();
         }

     }

     Box get (Coord coord){
         return bombMap.get(coord);
     }

     private void fixBombCount(){
         int maxBombsCount = Ranges.getSize().x + Ranges.getSize().y / 2;
         if(totalBomds > maxBombsCount){
             totalBomds = maxBombsCount;
         }
     }

     private void placeBomb(){
         while (true) {
             Coord coord = Ranges.getRandomCoord();
             if (Box.BOMB == bombMap.get(coord)) {
                 continue;
             }
             bombMap.set(coord, Box.BOMB);
             incNumbersAroundBomd(coord);
             break;
         }
     }


     private void incNumbersAroundBomd(Coord coord) {
         for(Coord aroung : Ranges.getCoordsAround(coord)){
             if(Box.BOMB != bombMap.get(aroung)) {
                 bombMap.set(aroung, bombMap.get(aroung).getNextNumberBox());
             }
         }
     }


     int getTotalBombs() {
         return totalBomds;
     }
 }
