package com.tools.is;

import com.tools.is.utils.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestAll {

    @Test
    public void test(){
        System.out.println(SnowflakeIdUtil.nextId());
    }


    public static void main(String[] args) {
        System.out.println(Math.floorMod(1608447338000l,10l));
    }
}
