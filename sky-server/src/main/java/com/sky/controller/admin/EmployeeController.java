package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController    //返回json格式并且使类能够处理http请求，等于@Controller+@ResponseBody
//@Controller
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);    //-----------------------接收的是前端传入的明文密码，所以需要加密

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 添加员工
     * @param employeeDTO
     * @return
     */
    @ApiOperation(value = "添加员工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }
//    @ApiOperation(value = "查询员工")
//    @PostMapping
//    public Result select(@RequestBody EmployeeDTO employeeDTO){
//
//        log.info("查询员工：{}", employeeDTO);
//
//        return Result.success(employeeService.select(employeeDTO));
//    }

    @ApiOperation(value = "查询员工")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO e){
        log.info("查询员工：{}", e);
        PageResult pageResult = employeeService.page(e);
        return Result.success(pageResult);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("修改员工状态")
    public Result banEmp(@PathVariable Integer status, long id){
        log.info("修改员工状态：{}，{}", status,id);
        employeeService.banEmp(status,id);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("查询员工信息")
    public Result<Employee> getEmployee(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        log.info("员工信息：{}", employee);
        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("修改员工信息：{}", employeeDTO);
        employeeService.updateById(employeeDTO);
        return Result.success();
    }

}
