# JDBC_ConnectionPool

ConnectionPool을 사용한다는것은 멀티스레드 환경일 가능성이 높다.
멀티스레드 환경에서 Transaction 처리는 매우 중요하다.
예를들어 상품을 주문하기 위해서는 장바구니의 데이터를 비우는 여러 작업들이 하나의 트랜잭션으로 이루어진다.
이러한 트랜잭션이 하나로 잘 처리 되기 위해서는 Connection 처리가 매우중요하다.
하나의 트랜잭션은 무조건 하나의 connection을 사용해야 하며
트랜잭션마다 connection을 매번 생성해줘야한다. 또한 connection을 받는 객체 또한 새롭게 만드는게 좋다.
