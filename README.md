1. 처음 설계한 API 명세서에 변경사항이 있었나요?
   변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요!
- API 명세서의 변경은 없었습니다.
- API 명세서를 설계한 대로 프로젝트를 해서 요청할 때 필요한 데이터와 응답받아야 할 데이터를 한 눈에 파악할 수 있었던 점이 좋았습니다.
2. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?
- ERD를 설계 후 변경은 있었지만 각 Entity의 필요한 field가 무엇인지 참고하여 프로젝트를 진행할 수 있었습니다.
3. JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?
- JWT를 최초 발급 후 토큰이 만료되지 않는 이상 이미 인증처리가 되었기 때문에 세션과 달리 서버에 부담이 적습니다.
- JWT 토큰을 사용하여 사용자 정보를 확인할 수 있기 때문에 별도의 인증 절차를 거치지 않고 게시글을 수정하거나 삭제할 수 있습니다.
4. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?
- 쿠키/세션에 저장하는 방식을 사용하지 않아 로그인 시 JWT를 최초로 발급한 후 발급된 토큰을 관리하기가 어렵습니다.
5. 만약 댓글 기능이 있는 블로그에서 댓글이 달려있는 게시글을 삭제하려고 한다면 무슨 문제가 발생할까요? Database 테이블 관점에서 해결방법이 무엇일까요?
- 게시글 Entity가 댓글 Entity를 참조하고 있기 때문에 해당 게시글을 삭제할 때 댓글도 모두 삭제가 되도록 설정해야 합니다. CASCADE ?? 뭐였더라
6. IoC / DI 에 대해 간략하게 설명해 주세요!
- IoC는 제어의 역전을 뜻한다.
- 객체를 생성할 때 멤버로 가지고 있는 다른 클래스 타입의 객체가 있다면 해당 객체는 멤버로 가지고 있는 객체에 의존하고 있다.
- A객체를 생성하기 위해 B객체를 생성해야 하며 제어의 흐름이 A객체 생성 -> B객체 생성이다.
- 외부에서 B객체를 생성 후 A객체의 생성자를 통해 B객체를 주입 받으면 제어의 흐름이 B -> A로 역전이된다.
- 이를 통해 A객체는 B객체의 의존성을 줄일 수 있고, 객체간 결합도를 낮춰 유연한 코드 작성 및 유지보수가 용이해진다.
- DI는 의존성을 주입하는 것을 말하며 외부에서 생성한 객체를 필요한 객체에 필드 주입, 메서드 주입, 생성자 주입 방식을 통해 객체를 주입하면서 객체간 결합도를 느슨하게 해줄 수 있다.