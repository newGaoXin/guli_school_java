package com.atguigu.eduservice.api;

import com.atguigu.commonutlis.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteBatchVideoByIds(List<String> ids) {
        return R.error().message("time out");
    }

    @Override
    public R deleteVideoById(String id) {
        return R.error().message("time out");
    }
}
