package cn.tangshh.stock_watcher.enums;

import cn.tangshh.stock_watcher.util.I18nUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;

import javax.swing.*;
import java.awt.*;

/**
 * 枚举列表单元格渲染器
 *
 * @author Tang
 * @version v1.0
 */
@Service
public final class IEnumListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof IEnum e) {
            setText(I18nUtil.message(e.getI18nKey()));
        }
        return this;
    }

    /**
     * 获取实例
     */
    public static IEnumListCellRenderer getInstance() {
        return ApplicationManager.getApplication().getService(IEnumListCellRenderer.class);
    }
}