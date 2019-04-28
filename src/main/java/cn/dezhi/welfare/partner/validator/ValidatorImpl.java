package cn.dezhi.welfare.partner.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author xjk
 * @date 2019/4/14 -  22:30
 **/
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    /**
     * 实现校验方法并返回结果
     * @return
     */
    public ValidationResult validate(Object bean) {
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgResult().put(propertyName,errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet()  {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
