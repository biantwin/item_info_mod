# 物品信息模组开发者指南

本文档详细介绍了物品信息模组的架构设计、扩展方法以及最佳实践，帮助开发者理解和扩展此模组的功能。

## 1. 项目概述

物品信息模组是一个 Minecraft Forge 模组，用于在玩家按下指定按键时显示手中物品的各种信息，包括食物营养价值、耐久度、NBT 数据等。

### 1.1 核心特性

- **模块化架构**：通过 [IItemInfoProvider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/IItemInfoProvider.java#L7-L18) 接口实现可扩展的信息显示系统
- **配置驱动**：支持通过配置文件控制不同信息类型的开关
- **命令控制**：提供 `/iteminfo` 命令动态调整功能设置
- **用户友好**：信息显示支持点击复制到剪贴板

## 2. 架构设计

### 2.1 核心组件

#### [ItemInfoMod](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/ItemInfoMod.java#L13-L88) (主入口类)
- 模组主类，负责初始化配置、注册事件监听器和命令
- 注册默认按键（B键）用于触发物品信息显示
- 监听配置更改和命令注册事件

#### [ItemInfoManager](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/ItemInfoManager.java#L9-L44) (信息管理器)
- 管理所有信息提供者实例
- 按预定义顺序调用各提供者来显示信息
- 提供添加/移除提供者的 API

#### [Config](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/Config.java#L7-L135) (配置管理)
- 管理所有功能开关的配置项
- 支持运行时动态修改配置
- 处理配置的保存和加载

#### [IItemInfoProvider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/IItemInfoProvider.java#L7-L18) (信息提供者接口)
- 定义信息提供者的标准接口
- 所有信息类型扩展都必须实现此接口

#### [MessageUtils](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/util/MessageUtils.java#L9-L51) (消息工具类)
- 提供统一的消息创建和发送功能
- 支持可点击复制的消息组件

### 2.2 信息提供者工作流程

1. 用户按下绑定的按键（默认B键）
2. [InputHandler](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/event/InputHandler.java#L7-L26) 触发信息请求
3. [ItemInfoManager](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/ItemInfoManager.java#L9-L44) 遍历所有注册的提供者
4. 每个提供者检查自身是否启用且适用于当前物品
5. 适用的提供者生成信息并发送给玩家

## 3. 扩展开发

### 3.1 添加新的信息类型

要添加新的信息类型，需要完成以下步骤：

#### 步骤1：创建信息提供者类

创建一个新的类实现 [IItemInfoProvider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/IItemInfoProvider.java#L7-L18) 接口：

```java
package biantwin.item_info_mod.info_provider;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 示例信息提供者 - 显示物品的某种属性
 */
public class ExampleInfoProvider implements IItemInfoProvider {
    
    @Override
    public boolean provideInfo(Player player, ItemStack itemStack) {
        // 检查功能是否启用
        if (!isEnabled()) {
            return false;
        }
        
        // 检查物品是否满足特定条件
        if (!isApplicable(itemStack)) {
            return false;
        }
        
        // 获取要显示的信息
        String exampleInfo = getExampleInfo(itemStack);
        
        // 发送信息给玩家
        String message = "§a[示例信息] §f物品的示例属性: §b" + exampleInfo;
        MessageUtils.sendCopyableMessageToPlayer(player, message, exampleInfo);
        
        return true;
    }
    
    @Override
    public String getName() {
        return "exampleInfo";
    }
    
    @Override
    public boolean isEnabled() {
        // 返回对应的配置项
        return Config.isEnableExampleInfo();
    }
    
    /**
     * 检查物品是否适用于此信息提供者
     */
    private boolean isApplicable(ItemStack itemStack) {
        // 在这里实现判断逻辑
        // 例如：检查物品类型、NBT数据等
        return true; // 示例：所有物品都适用
    }
    
    /**
     * 获取示例信息
     */
    private String getExampleInfo(ItemStack itemStack) {
        // 在这里实现获取信息的逻辑
        return "示例信息"; // 示例返回值
    }
}
```

#### 步骤2：添加配置项

在 [Config.java](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/Config.java#L7-L135) 文件中添加配置项：

1. 在类的成员变量部分添加：

```java
private static ForgeConfigSpec.BooleanValue enableExampleInfoSpec;
public static boolean enableExampleInfo = true; // 默认值
```

2. 在构造函数中添加配置定义：

```java
enableExampleInfoSpec = builder
        .comment("是否启用示例信息功能.")
        .translation("item_info_mod.config.enableExampleInfo")
        .define("enableExampleInfo", true);
```

3. 添加获取配置值的方法：

```java
public static boolean isEnableExampleInfo() {
    try {
        return enableExampleInfoSpec != null ? enableExampleInfoSpec.get() : enableExampleInfo;
    } catch (Exception e) {
        // 如果配置未初始化，返回静态默认值
        return enableExampleInfo;
    }
}
```

4. 添加设置配置值的方法：

```java
public static void setEnableExampleInfo(boolean value) {
    enableExampleInfo = value;
}
```

5. 在 [sync()](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/Config.java#L116-L122) 方法中添加同步：

```java
enableExampleInfo = enableExampleInfoSpec.get();
```

#### 步骤3：添加命令支持（可选）

如果需要通过命令控制新功能，需要在 [CommandHandler.java](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/command/CommandHandler.java#L9-L87) 中添加命令处理方法：

```java
public static int enableExampleInfo(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    Config.setEnableExampleInfo(true);
    context.getSource().sendSuccess(() -> Component.literal("§a示例信息显示已启用"), false);
    return 1;
}

public static int disableExampleInfo(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    Config.setEnableExampleInfo(false);
    context.getSource().sendSuccess(() -> Component.literal("§c示例信息显示已禁用"), false);
    return 1;
}
```

并在 [ItemInfoMod.java](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/ItemInfoMod.java#L13-L88) 的命令注册部分添加新命令。

#### 步骤4：注册信息提供者

在 [ItemInfoManager.java](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/ItemInfoManager.java#L9-L44) 的静态初始化块中注册新的提供者：

```java
static {
    // 注册所有信息提供者
    providers.add(new NBTInfoProvider());          // NBT信息（最高优先级）
    providers.add(new DurabilityInfoProvider());   // 耐久信息
    providers.add(new FoodInfoProvider());         // 食物信息
    providers.add(new ItemNameInfoProvider());     // 物品名称（最低优先级）
    providers.add(new ExampleInfoProvider());      // 新增：示例信息提供者
}
```

> 注意：提供者的顺序很重要，因为 [ItemInfoManager](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/ItemInfoManager.java#L9-L44) 会按顺序调用它们。根据信息的重要性和通用性安排合适的顺序。

## 4. 开发最佳实践

### 4.1 信息提供者设计原则

1. **单一职责**：每个信息提供者只负责一种类型的信息
2. **条件检查**：在提供信息前，先检查物品是否符合条件
3. **配置检查**：始终检查功能是否已启用
4. **返回值**：成功提供信息时返回 `true`，否则返回 `false`
5. **性能考虑**：避免在 [provideInfo()](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/IItemInfoProvider.java#L15-L16) 方法中执行耗时操作

### 4.2 消息格式规范

- 使用颜色代码增强可读性：
  - `§a` - 绿色（标题）
  - `§f` - 白色（描述）
  - `§b` - 蓝色（数值）
  - `§c` - 红色（警告/错误）
- 标题格式：`§a[信息类别] §f描述: §b值`
- 利用 [MessageUtils](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/util/MessageUtils.java#L9-L51) 类创建可点击复制的消息

### 4.3 配置管理最佳实践

1. 为每个功能添加独立的配置开关
2. 提供合理的默认值
3. 在配置未初始化时提供降级方案
4. 实现运行时配置同步

### 4.4 异常处理

- 在访问配置项时使用 try-catch 块
- 提供默认值作为异常时的回退方案
- 记录重要的错误日志以便调试

## 5. 现有信息提供者分析

### 5.1 [FoodInfoProvider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/FoodInfoProvider.java#L9-L48)
- 显示食物的饱食度和饱和度
- 仅对可食用物品生效
- 计算实际饱和度值（营养值 × 饱和度倍数）

### 5.2 [DurabilityInfoProvider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/DurabilityInfoProvider.java#L8-L49)
- 显示工具和武器的耐久度信息
- 仅对可损坏物品生效
- 计算并显示耐久百分比

### 5.3 [NBTInfoProvider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/NBTInfoProvider.java#L8-L48)
- 显示物品的 NBT 数据
- 适用于所有带有 NBT 数据的物品
- 格式化 NBT 内容以便阅读

### 5.4 [ItemNameInfoProvider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider/ItemNameInfoProvider.java#L8-L34)
- 显示物品的标准名称
- 通常作为最后的信息显示
- 适用于所有物品

## 6. 测试与调试

### 6.1 功能测试清单

- [ ] 不同类型物品的信息显示
- [ ] 配置开关功能正常
- [ ] 命令控制功能正常
- [ ] 按键触发功能正常
- [ ] 消息复制功能正常
- [ ] 信息提供者顺序正确

### 6.2 调试技巧

1. 使用日志记录信息提供者的执行情况
2. 测试各种物品类型确保信息提供者正确工作
3. 验证配置开关是否按预期工作
4. 检查命令是否能正确控制功能开关
5. 验证信息提供者顺序是否符合预期

## 7. 常见问题及解决方案

### 7.1 信息不显示
- 检查配置项是否正确启用
- 确认物品是否满足信息提供者的条件
- 验证信息提供者是否已注册

### 7.2 多个信息同时显示
- 调整信息提供者的顺序
- 检查条件判断逻辑是否过于宽松

### 7.3 配置无法保存
- 确保配置文件路径正确
- 验证配置同步逻辑是否正常

### 7.4 性能问题
- 优化信息获取算法
- 减少不必要的 NBT 或其他复杂数据访问
- 考虑添加缓存机制

## 8. 项目维护建议

### 8.1 代码组织
- 将信息提供者放在 [info_provider](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/info_provider) 包中
- 将工具类放在 [util](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/util) 包中
- 将事件处理器放在 [event](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/event) 包中
- 将命令处理器放在 [command](file:///D:/Code/ModText/item_info_mod/src/main/java/biantwin/item_info_mod/command) 包中

### 8.2 文档维护
- 更新开发者文档以反映新功能
- 维护用户文档说明新特性
- 记录 API 变更历史

通过遵循本指南，您可以有效地向物品信息模组添加新功能，同时保持代码的可维护性和扩展性。