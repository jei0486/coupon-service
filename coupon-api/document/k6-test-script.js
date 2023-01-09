import http from 'k6/http';
import { sleep, check } from 'k6';

/*

파일명 : k6-test-script.js

쉘 명령어 입력 :
k6 run --vus 1000 --duration 30s --out json=out.json k6-test-script.js
*/

export default function () {

    let rand = Math.floor( Math.random() * 100 ) + 1;

    const body = {
        user_id: `user_${rand}` ,
        key : 'coupon:time-attack:20:date-time:2:issued:users'
    };

    console.log( JSON.stringify(body));
    const params = { headers: {'Content-Type': 'application/json'} };
    const res = http.post('http://localhost:8081/coupon/issued', JSON.stringify(body), params);

    // check : 요청의 결과값이 적절한지 확인하는 함수
    check(res, {
        'response code was 200': (res) => console.log("response : " + res.body),
    });

    sleep(0);

}
