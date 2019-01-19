package aspects;

import games.ConnectFour;

public aspect MyFirstAcpect {

    pointcut callDrawPlayingField(): call(* ConnectFour.drawPlayingField());
 
    before() : callDrawPlayingField() {
        System.out.println("Before drawing play field");
    }
 
    after() : callDrawPlayingField()  {
        System.out.println("After drawing play field");
    }
}

