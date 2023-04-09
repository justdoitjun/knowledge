    //forEach구문은 매개변수로 함수가 전달된다. 
    //매개체가 되는 변수,  함수외부에서 함수내부로 값을 전달할때 쓰이는
    //변수이다. 
    //forEach함수한테 함수를 준다.  매개변수하나 반환값이 없는 함수를 
    //구성해서 전달해야 한다.  주로 출력용으로 준다 
    flowers.forEach( (x, i)=>{
        console.log(x, i);
    });
    
    let flag=false;
    i=0;
    j=0; 
    while(true)
    {
        i=i+1;
        while(true)
        {
            console.log( i, j);
            j=j+1;
            if(j>=5)
            {
                flag=true; 
                break; 
            }
        }
        if(flag)
            break;
    }